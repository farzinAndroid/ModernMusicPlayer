package com.farzin.core_data.repository

import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import com.farzin.core_model.UserData

interface SharedPreferencesRepository {

    suspend fun getUserData(): UserData

    suspend fun setPlayingQueueIds(playingQueueIds: List<String>)
    suspend fun setPlayingQueueIndex(playingQueueIndex: Int)
    suspend fun setPlaybackMode(playbackMode: PlaybackMode)
    suspend fun setSortOrder(sortOrder: SortOrder)
    suspend fun setSortBy(sortBy: SortBy)


    companion object{
        val PLAYING_QUEUE_ID_KEY = "PLAYING_QUEUE_ID_KEY"
        val PLAYING_QUEUE_INDEX_KEY = "PLAYING_QUEUE_INDEX_KEY"
        val PLAY_BACK_MODE_KEY = "PLAY_BACK_MODE_KEY"
        val SORT_ORDER_KEY = "SORT_ORDER_KEY"
        val SORT_BY_KEY = "SORT_BY_KEY"
    }


}