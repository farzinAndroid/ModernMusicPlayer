package com.farzin.core_domain.repository

import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import com.farzin.core_model.UserData
import kotlinx.coroutines.flow.Flow

interface SharedPreferencesRepository {

    suspend fun getUserData(): Flow<UserData>

    suspend fun setPlayingQueueIds(playingQueueIds: List<String>)
    suspend fun setPlayingQueueIndex(playingQueueIndex: Int)
    suspend fun setPlaybackMode(playbackMode: PlaybackMode)
    suspend fun setSortOrder(sortOrder: SortOrder)
    suspend fun setSortBy(sortBy: SortBy)





    companion object{
        const val PLAYING_QUEUE_ID_KEY = "PLAYING_QUEUE_ID_KEY"
        const val PLAYING_QUEUE_INDEX_KEY = "PLAYING_QUEUE_INDEX_KEY"
        const val PLAY_BACK_MODE_KEY = "PLAY_BACK_MODE_KEY"
        const val SORT_ORDER_KEY = "SORT_ORDER_KEY"
        const val SORT_BY_KEY = "SORT_BY_KEY"
        const val REPEAT_MODE = "REPEAT_MODE"
        const val SHUFFLE_MODE = "SHUFFLE_MODE"
    }


}