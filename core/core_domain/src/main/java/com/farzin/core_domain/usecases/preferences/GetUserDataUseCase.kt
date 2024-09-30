package com.farzin.core_domain.usecases.preferences

import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    operator fun invoke()=sharedPreferencesRepository.userData
    }