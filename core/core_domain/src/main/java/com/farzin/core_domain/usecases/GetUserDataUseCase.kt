package com.farzin.core_domain.usecases

import com.farzin.core_data.preferences.DefaultPreferences
import com.farzin.core_model.UserData
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val defaultPreferences: DefaultPreferences) {

    suspend operator fun invoke(): UserData {
        return defaultPreferences.getUserData()
    }
}