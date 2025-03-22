package com.farzin.core_domain.usecases.preferences

import com.farzin.core_domain.repository.preferences.SharedPreferencesRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke()=sharedPreferencesRepository.userData
    }