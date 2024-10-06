package com.farzin.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.media.MediaUseCases
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_media_service.MusicServiceConnection
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.Song
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewmodel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection,
    private val preferencesUseCases: PreferencesUseCases
) : ViewModel() {

    val musicState = musicServiceConnection.musicState
    val currentPosition = musicServiceConnection.currentPosition.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = 0L
    )

    fun play(
        songs: List<Song>,
        startIndex: Int = 0,
    ) = musicServiceConnection.playSongs(
        songs, startIndex
    )

    fun pausePlay(isPlaying: Boolean) =
        if (isPlaying) {
            musicServiceConnection.play()
        } else {
            musicServiceConnection.pause()
        }


    fun skipNext() = musicServiceConnection.skipNext()
    fun skipPrevious() = musicServiceConnection.skipPrevious()

    fun skipToIndex(index: Int) = musicServiceConnection.skipToIndex(index)

    fun seekTo(position: Long) = musicServiceConnection.seekTo(position)


    val playbackMode = preferencesUseCases.getPlaybackModeUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = PlaybackMode.REPEAT
    )

    fun onTogglePlaybackMode() = viewModelScope.launch {
        val newPlaybackMode = when (playbackMode.value) {
            PlaybackMode.REPEAT -> PlaybackMode.REPEAT_ONE
            PlaybackMode.REPEAT_ONE -> PlaybackMode.SHUFFLE
            PlaybackMode.SHUFFLE -> PlaybackMode.REPEAT
        }
        preferencesUseCases.setPlaybackModeUseCase(newPlaybackMode)
    }

    fun onChangeSortOrder(sortOrder: SortOrder) =
        viewModelScope.launch { preferencesUseCases.setSortOrderUseCase(sortOrder) }

    fun onChangeSortBy(sortBy: SortBy) =
        viewModelScope.launch { preferencesUseCases.setSortByUseCase(sortBy) }

}