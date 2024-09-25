package com.farzin.core_domain.usecases.preferences

import com.farzin.core_domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetPlayingQueueIdsUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) {
    suspend operator fun invoke() = runBlocking {
        sharedPreferencesRepository.getUserData().map {
            it.playingQueueIds
        }
    }

}