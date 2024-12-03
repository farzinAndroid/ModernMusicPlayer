package com.farzin.core_data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.farzin.core_model.db.Playlist
import com.farzin.core_model.db.PlaylistSong

@Dao
interface PlaylistDao {

    @Insert
    suspend fun createPlaylist(playlist: Playlist)

    @Insert
    suspend fun insertPlaylistSongs(playlistSong: List<PlaylistSong>)

    @Query("select * from playlist")
    suspend fun getAllPlaylists() : List<Playlist>

    @Query("select * from playlist_song where playlistId = :playlistId")
    suspend fun getSongsInPlaylist(playlistId: Int) : List<PlaylistSong>
}