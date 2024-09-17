package com.farzin.core_domain.usecases.preferences

import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_model.SortOrder
import javax.inject.Inject

class SetSortOrderUseCase @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {
    suspend operator fun invoke(sortOrder: SortOrder) {
        sharedPreferencesRepository.setSortOrder(sortOrder)
    }
}