package com.farzin.core_domain.repository

import com.farzin.core_model.db.Playlist
import com.farzin.core_model.db.PlaylistSong
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun createPlaylist(playlist: Playlist)
    suspend fun insertPlaylistSongs(playlistSong: List<PlaylistSong>)
    suspend fun deletePlaylist(playlist: Playlist)
    fun getAllPlaylists() : Flow<List<Playlist>>
    fun getSongsInPlaylist(playlistId: Int) : List<PlaylistSong>
}