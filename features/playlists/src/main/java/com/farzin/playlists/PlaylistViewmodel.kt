package com.farzin.playlists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.db.PlaylistUseCases
import com.farzin.core_domain.usecases.media.MediaUseCases
import com.farzin.core_model.db.Playlist
import com.farzin.core_model.db.PlaylistSong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewmodel @Inject constructor(
    private val playlistUseCases: PlaylistUseCases,
    private val mediaUseCases: MediaUseCases
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


    val songsInPlaylist = MutableStateFlow<List<PlaylistSong>>(emptyList())
    fun getSongsInPlaylist(playlistId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            songsInPlaylist.emit(playlistUseCases.getSongsInPlaylistUseCase(playlistId))
        }
    }

    fun insertPlaylistSong(playlistSong: List<PlaylistSong>) = viewModelScope.launch(Dispatchers.IO) {
        playlistUseCases.insertPlaylistSongUseCase(playlistSong)
    }

}