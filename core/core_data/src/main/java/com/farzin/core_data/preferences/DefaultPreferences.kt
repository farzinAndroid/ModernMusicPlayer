package com.farzin.core_data.preferences

import android.content.SharedPreferences
import com.farzin.core_data.domain.usecases.TurnPlayQueueIdToListUseCase
import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_model.EnumUtils
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import com.farzin.core_model.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val turnPlayQueueIdToListUseCase: TurnPlayQueueIdToListUseCase,
) : SharedPreferencesRepository {

    override suspend fun setPlayingQueueIds(playingQueueIds: List<String>) {
        withContext(Dispatchers.IO) {
            sharedPreferences
                .edit()
                .putString(
                    SharedPreferencesRepository.PLAYING_QUEUE_ID_KEY,
                    playingQueueIds.joinToString(",")
                )
                .apply()
        }
    }

    override suspend fun setPlayingQueueIndex(playingQueueIndex: Int) {
        withContext(Dispatchers.IO) {
            sharedPreferences
                .edit()
                .putInt(SharedPreferencesRepository.PLAYING_QUEUE_INDEX_KEY, playingQueueIndex)
                .apply()
        }
    }

    override suspend fun setPlaybackMode(playbackMode: PlaybackMode) {
        withContext(Dispatchers.IO) {
            sharedPreferences
                .edit()
                .putString(SharedPreferencesRepository.PLAY_BACK_MODE_KEY, playbackMode.name)
                .apply()
        }
    }

    override suspend fun setSortOrder(sortOrder: SortOrder) {
        withContext(Dispatchers.IO) {
            sharedPreferences
                .edit()
                .putString(SharedPreferencesRepository.SORT_ORDER_KEY, sortOrder.name)
                .apply()
        }
    }

    override suspend fun setSortBy(sortBy: SortBy) {
        withContext(Dispatchers.IO) {
            sharedPreferences
                .edit()
                .putString(SharedPreferencesRepository.SORT_BY_KEY, sortBy.name)
                .apply()
        }
    }


    override suspend fun getUserData(): Flow<UserData> =
        withContext(Dispatchers.IO) {
            val savedPlayingQueueIdsString =
                sharedPreferences.getString(SharedPreferencesRepository.PLAYING_QUEUE_ID_KEY, null)
                    ?: ""
            val savedPlayingQueueIdsList =
                turnPlayQueueIdToListUseCase.invoke(savedPlayingQueueIdsString)

            val savedPlayingQueueIndex =
                sharedPreferences.getInt(SharedPreferencesRepository.PLAYING_QUEUE_INDEX_KEY, -1)
            val savedPlaybackMode =
                sharedPreferences.getString(SharedPreferencesRepository.PLAY_BACK_MODE_KEY, null)
                    ?: ""
            val savedSortOrder =
                sharedPreferences.getString(SharedPreferencesRepository.SORT_ORDER_KEY, null) ?: ""
            val savedSortBy =
                sharedPreferences.getString(SharedPreferencesRepository.SORT_BY_KEY, null) ?: ""

            val userData = UserData(
                playingQueueIds = savedPlayingQueueIdsList,
                playingQueueIndex = savedPlayingQueueIndex,
                sortOrder = EnumUtils.fromStringSortOrder(savedSortOrder),
                sortBy = EnumUtils.fromStringSortBy(savedSortBy),
                playbackMode = EnumUtils.fromStringPlaybackMode(savedPlaybackMode)
            )

            return@withContext flowOf(userData)
        }


}