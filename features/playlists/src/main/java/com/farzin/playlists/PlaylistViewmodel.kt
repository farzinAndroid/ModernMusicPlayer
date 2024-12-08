package com.farzin.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.db.PlaylistUseCases
import com.farzin.core_model.db.Playlist
import com.farzin.core_model.db.PlaylistSong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewmodel @Inject constructor(
    private val playlistUseCases: PlaylistUseCases,
) : ViewModel() {



    val songsInPlaylist = MutableStateFlow<List<PlaylistSong>>(emptyList())
    fun getSongsInPlaylist(playlistId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            songsInPlaylist.emit(playlistUseCases.getSongsInPlaylistUseCase(playlistId))
        }
    }

    fun createPlaylist(playlist: Playlist) = viewModelScope.launch(Dispatchers.IO) {
        playlistUseCases.createPlaylistUseCase(playlist)
    }

    fun insertPlaylistSong(playlistSong: List<PlaylistSong>) = viewModelScope.launch(Dispatchers.IO) {
        playlistUseCases.insertPlaylistSongUseCase(playlistSong)
    }

}