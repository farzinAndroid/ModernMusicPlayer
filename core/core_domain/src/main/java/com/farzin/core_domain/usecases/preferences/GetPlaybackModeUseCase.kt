package com.farzin.core_domain.usecases.preferences

import com.farzin.core_domain.repository.preferences.SharedPreferencesRepository
import com.farzin.core_model.PlaybackMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPlaybackModeUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) {
    operator fun invoke(): Flow<PlaybackMode> {
        return sharedPreferencesRepository.userData.map { it.playbackMode }
    }
}