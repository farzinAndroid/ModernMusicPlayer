package com.farzin.core_data.repository

import com.farzin.core_datastore.PreferencesDataSource
import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import com.farzin.core_model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
) : SharedPreferencesRepository {
    override val userData: Flow<UserData> = preferencesDataSource.userData

    override suspend fun setPlayingQueueIds(playingQueueIds: List<String>) =
        preferencesDataSource.setPlayingQueueIds(playingQueueIds)

    override suspend fun setPlayingQueueIndex(playingQueueIndex: Int) =
        preferencesDataSource.setPlayingQueueIndex(playingQueueIndex)

    override suspend fun setPlaybackMode(playbackMode: PlaybackMode) =
        preferencesDataSource.setPlaybackMode(playbackMode)

    override suspend fun setSortOrder(sortOrder: SortOrder) =
        preferencesDataSource.setSortOrder(sortOrder)

    override suspend fun setSortBy(sortBy: SortBy) = preferencesDataSource.setSortBy(sortBy)

    override suspend fun toggleFavoriteSong(id: String, isFavorite: Boolean) =
        preferencesDataSource.toggleFavoriteSong(id, isFavorite)
}