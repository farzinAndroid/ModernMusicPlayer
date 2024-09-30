package com.farzin.core_domain.usecases.preferences

import com.farzin.core_domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPlayingQueueIdsUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) {
    operator fun invoke() = sharedPreferencesRepository.userData.map { it.playingQueueIds }

}