package com.farzin.search.search

import com.farzin.core_model.SearchDetails
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder

sealed interface SearchUiState {
    data object Loading : SearchUiState

    data class Success(
        val query: String,
        val searchDetails: SearchDetails,
    ) : SearchUiState
}
