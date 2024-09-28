package com.farzin.core_domain.usecases.preferences

import com.farzin.core_domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetRepeatModeUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) {
    operator fun invoke():Int = sharedPreferencesRepository.getRepeatMode()

}