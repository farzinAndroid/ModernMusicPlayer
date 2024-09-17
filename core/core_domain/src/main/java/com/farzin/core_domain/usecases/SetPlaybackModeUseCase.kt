package com.farzin.core_domain.usecases

import com.farzin.core_data.preferences.DefaultPreferences
import com.farzin.core_model.PlaybackMode
import javax.inject.Inject

class SetPlaybackModeUseCase @Inject constructor(
    private val defaultPreferences: DefaultPreferences
) {
    suspend operator fun invoke(playbackMode: PlaybackMode) {
        defaultPreferences.setPlaybackMode(playbackMode)
    }
}