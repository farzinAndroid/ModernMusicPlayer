package com.farzin.core_domain.usecases.preferences

import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_model.PlaybackMode
import javax.inject.Inject

class SetRepeatModeUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    suspend operator fun invoke(value:Int) {
        sharedPreferencesRepository.setRepeatMode(value)
    }
}