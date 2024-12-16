package com.farzin.playlists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.db.PlaylistUseCases
import com.farzin.core_domain.usecases.media.MediaUseCases
import com.farzin.core_model.Song
import com.farzin.core_model.db.PlaylistSong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewmodel @Inject constructor(
    private val playlistUseCases: PlaylistUseCases,
    private val mediaUseCases: MediaUseCases,
) : ViewModel() {

    var showAddSongToPlaylistDialog by mutableStateOf(false)

    val playingQueueSongs = mediaUseCases.getPlayingQueueSongsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )


    val songs = mediaUseCases.getSongsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )


    val allSongsInAllPlaylists = playlistUseCases.getSongsInAllPlaylistsUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )


    fun isSongInPlaylist(song: Song): Boolean {
        return allSongsInAllPlaylists.value.any { it.song.mediaId == song.mediaId }
    }


    fun songsInPlaylist(playlistId: Int) = playlistUseCases.getSongsInPlaylistUseCase(playlistId)

    fun insertPlaylistSong(playlistSongs: List<PlaylistSong>,playlistId:Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val playlistSongsToInsert = playlistSongs.map { originalPlaylistSong ->
                PlaylistSong(
                    song = originalPlaylistSong.song, // Important: Keep the same song
                    playlistId = playlistId, // Set the new playlistId
                    id = "${originalPlaylistSong.song.mediaId}_$playlistId"
                )
            }

            playlistUseCases.insertPlaylistSongUseCase(playlistSongsToInsert)
        }

    fun deleteSongFromPlaylist(playlistSong: PlaylistSong) =
        viewModelScope.launch(Dispatchers.IO) {
            val playlistSongToDelete = PlaylistSong(
                song = playlistSong.song,
                playlistId = playlistSong.playlistId,
                id = "${playlistSong.song.mediaId}_${playlistSong.playlistId}" // Generate the correct ID
            )
            playlistUseCases.deleteSongInPlaylistUseCase(playlistSongToDelete)
        }

}