package com.farzin.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_media_service.MusicServiceConnection
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PreferencesViewmodel @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases,
    musicServiceConnection: MusicServiceConnection,
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

    fun onChangeSortOrder(sortOrder: SortOrder) =
        viewModelScope.launch { preferencesUseCases.setSortOrderUseCase(sortOrder) }

    fun onChangeSortBy(sortBy: SortBy) =
        viewModelScope.launch { preferencesUseCases.setSortByUseCase(sortBy) }

}