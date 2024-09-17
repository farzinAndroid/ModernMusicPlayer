package com.farzin.core_domain.usecases

import com.farzin.core_domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SetPlayingQueueIndexUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    suspend operator fun invoke(playingQueueIndex:Int) {
        sharedPreferencesRepository.setPlayingQueueIndex(playingQueueIndex)
    }
}