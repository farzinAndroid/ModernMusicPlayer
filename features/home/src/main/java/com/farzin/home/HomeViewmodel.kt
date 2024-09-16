package com.farzin.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_data.preferences.DefaultPreferences
import com.farzin.core_domain.repository.MediaRepository
import com.farzin.core_model.PlaybackMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val defaultPreferences: DefaultPreferences,
): ViewModel() {

    fun setPlayBackMode(playbackMode: PlaybackMode){
        viewModelScope.launch {
            defaultPreferences.setPlaybackMode(playbackMode)
        }
    }

    fun getUserData() = runBlocking {
        defaultPreferences.getUserData()
    }

}