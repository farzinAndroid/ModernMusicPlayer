package com.farzin.core_data.repository

import android.net.Uri
import com.farzin.core_datastore.PreferencesDataSource
import com.farzin.core_domain.repository.MediaRepository
import com.farzin.core_model.Album
import kotlin.time.Duration.Companion.days
import com.farzin.core_model.Artist
import com.farzin.core_model.Folder
import com.farzin.core_model.Song
import com.farzin.media_store.source.MediaStoreSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.Duration
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaStoreSource: MediaStoreSource,
    preferencesDataSource: PreferencesDataSource,
) : MediaRepository {


    @OptIn(ExperimentalCoroutinesApi::class)
    override val songs: Flow<List<Song>> =
        preferencesDataSource.userData
            .flatMapLatest { userData ->
                mediaStoreSource.getSongs(
                    sortOrder = userData.sortOrder,
                    sortBy = userData.sortBy,
                    favoriteSongs = userData.favoriteSongs,
//                    excludedFolders = excludedFolders
                )
            }


    override val recentlyAdded: Flow<List<Song>> =songs.map { songList ->
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        songList.filter { song ->
            val daysSinceAdded = Duration.between(song.date.toJavaLocalDateTime(), now.toJavaLocalDateTime()).toDays()
            daysSinceAdded <= 5
        }
    }



    override val artists: Flow<List<Artist>> = songs.map { songs ->
        songs.groupBy { it.artistId }.map { (artistId, songs) ->
            val firstSong = songs.first() // to get the name
            Artist(
                id = artistId,
                name = firstSong.artist,
                songs = songs
            )
        }

    }


    override val albums: Flow<List<Album>> = songs.map { songs ->
        songs.groupBy { it.albumId }.map { (albumId, songs) ->
            val firstSong = songs.first() // to get the album / artworkUri / artist
            Album(
                id = albumId,
                name = firstSong.album,
                songs = songs,
                artworkUri = firstSong.artworkUri,
                artist = firstSong.artist
            )
        }
    }


    override val folders: Flow<List<Folder>> = songs.map { songs ->
        songs.groupBy { it.folder }.map { (folder, songs) ->
            Folder(
                name = folder,
                songs = songs
            )
        }
    }


}