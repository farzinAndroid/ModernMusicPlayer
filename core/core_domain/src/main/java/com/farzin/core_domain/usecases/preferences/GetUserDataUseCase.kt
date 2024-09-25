package com.farzin.core_domain.usecases.preferences

import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    suspend operator fun invoke(): Flow<UserData> {
        return sharedPreferencesRepository.getUserData()
    }
}