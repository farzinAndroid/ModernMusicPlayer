package com.farzin.core_domain.usecases.preferences

import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_model.SortBy
import javax.inject.Inject

class SetSortByUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    suspend operator fun invoke(sortBy: SortBy) {
        sharedPreferencesRepository.setSortBy(sortBy)
    }
}