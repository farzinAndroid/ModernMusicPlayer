package com.farzin.core_model

data class UserData(
    val playingQueueIds: List<String>,
    val playingQueueIndex: Int,
    val playbackMode: PlaybackMode,
    val sortOrder: SortOrder,
    val sortBy: SortBy,
)