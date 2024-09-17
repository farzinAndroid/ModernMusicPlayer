package com.farzin.core_domain.usecases

import com.farzin.core_data.preferences.DefaultPreferences
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import javax.inject.Inject

class SetPlayingQueueIdsUseCase @Inject constructor(
    private val defaultPreferences: DefaultPreferences
) {
    suspend operator fun invoke(playingQueueIds: List<String>) {
        defaultPreferences.setPlayingQueueIds(playingQueueIds)
    }
}