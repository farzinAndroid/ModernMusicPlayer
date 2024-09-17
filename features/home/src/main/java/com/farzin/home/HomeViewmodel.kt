package com.farzin.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_data.preferences.DefaultPreferences
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val defaultPreferences: DefaultPreferences,
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
            defaultPreferences.setPlaybackMode(playbackMode)
            _userData.value = getUserData()
        }
    }

    private suspend fun getUserData(): UserData {
        return withContext(Dispatchers.IO) {
            defaultPreferences.getUserData()
        }
    }

}