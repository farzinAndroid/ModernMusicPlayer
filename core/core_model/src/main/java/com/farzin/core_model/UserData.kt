package com.farzin.core_model

data class UserData(
    val playingQueueIds: List<String> = emptyList(),
    val playingQueueIndex: Int = -1,
    val playbackMode: PlaybackMode = PlaybackMode.REPEAT,
    val sortOrder: SortOrder = SortOrder.DESCENDING,
    val sortBy: SortBy = SortBy.TITLE,
)