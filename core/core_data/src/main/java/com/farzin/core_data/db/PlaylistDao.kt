package com.farzin.core_data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.farzin.core_domain.repository.PlaylistRepository
import com.farzin.core_model.db.Playlist
import com.farzin.core_model.db.PlaylistSong
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao : PlaylistRepository {

    @Insert
    override suspend fun createPlaylist(playlist: Playlist)

    @Delete
    override suspend fun deletePlaylist(playlist: Playlist)

    @Insert
    override suspend fun insertPlaylistSongs(playlistSong: List<PlaylistSong>)

    @Query("select * from playlist")
    override fun getAllPlaylists() : Flow<List<Playlist>>

    @Query("select * from playlist_song where playlistId = :playlistId")
    override fun getSongsInPlaylist(playlistId: Int) : List<PlaylistSong>
}