package com.farzin.core_data.repository

import com.farzin.core_domain.repository.MediaRepository
import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_model.Album
import com.farzin.core_model.Artist
import com.farzin.core_model.Folder
import com.farzin.core_model.Song
import com.farzin.core_model.UserData
import com.farzin.media_store.source.MediaStoreSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaStoreSource: MediaStoreSource,
    private val defaultPreferences: SharedPreferencesRepository,
) : MediaRepository {


    var userData: UserData = UserData()

    init {
        userData = runBlocking {
            defaultPreferences.getUserData()
        }
    }

    override val songs: Flow<List<Song>> =
        mediaStoreSource.getSongs(
            sortOrder = userData.sortOrder,
            sortBy = userData.sortBy
        )


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