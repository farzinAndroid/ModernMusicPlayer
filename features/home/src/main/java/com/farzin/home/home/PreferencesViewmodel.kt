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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class PreferencesViewmodel @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases,
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    private val _userData = MutableStateFlow<Flow<UserData>>(emptyFlow())
    val userData: StateFlow<Flow<UserData>> = _userData

    init {
        viewModelScope.launch {
            _userData.value = getUserData()
        }
    }

    fun setPlayBackMode(playbackMode: PlaybackMode){
        viewModelScope.launch {
            preferencesUseCases.setPlaybackModeUseCase(playbackMode)
            musicServiceConnection.setPlaybackMode(playbackMode)
            _userData.value = getUserData()
        }
    }

    fun getUserData(): Flow<UserData> {
        return runBlocking {
            preferencesUseCases.getUserDataUseCase()
        }
    }

    fun setSortOrder(sortOrder: SortOrder){
        viewModelScope.launch {
            preferencesUseCases.setSortOrderUseCase(sortOrder)
            _userData.value = getUserData()
        }
    }

}