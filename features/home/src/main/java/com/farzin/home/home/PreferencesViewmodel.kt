package com.farzin.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_media_service.MusicServiceConnection
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.SortOrder
import com.farzin.core_model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class PreferencesViewmodel @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases,
    musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    val musicState = musicServiceConnection.musicState

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

}