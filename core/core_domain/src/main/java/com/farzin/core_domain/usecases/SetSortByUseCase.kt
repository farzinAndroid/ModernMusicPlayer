package com.farzin.core_domain.usecases

import com.farzin.core_data.preferences.DefaultPreferences
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.SortBy
import javax.inject.Inject

class SetSortByUseCase @Inject constructor(
    private val defaultPreferences: DefaultPreferences
) {
    suspend operator fun invoke(sortBy: SortBy) {
        defaultPreferences.setSortBy(sortBy)
    }
}