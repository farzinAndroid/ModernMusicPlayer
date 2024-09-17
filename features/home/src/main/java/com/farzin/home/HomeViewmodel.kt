package com.farzin.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.PreferencesUseCases
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases
): ViewModel() {

    private val _userData = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> = _userData

    init {
        viewModelScope.launch {
            _userData.value = getUserData()
        }
    }

    fun setPlayBackMode(playbackMode: PlaybackMode){
        viewModelScope.launch {
            preferencesUseCases.setPlaybackModeUseCase(playbackMode)
            _userData.value = getUserData()
        }
    }

    fun getUserData(): UserData {
        return runBlocking {
            preferencesUseCases.getUserDataUseCase()
        }
    }

}