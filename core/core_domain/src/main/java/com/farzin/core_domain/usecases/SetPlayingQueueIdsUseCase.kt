package com.farzin.core_domain.usecases

import com.farzin.core_domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SetPlayingQueueIdsUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    suspend operator fun invoke(playingQueueIds: List<String>) {
        sharedPreferencesRepository.setPlayingQueueIds(playingQueueIds)
    }
}