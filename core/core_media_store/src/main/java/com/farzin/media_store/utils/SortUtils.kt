package com.farzin.media_store.utils

import android.provider.MediaStore
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder

object SortUtils {

    fun getSortOrder(sortOrder: SortOrder, sortBy: SortBy): String {
        return "${mediaSortBySortBy.getValue(sortBy)} ${mediaSortBySortOrder.getValue(sortOrder)}"
    }

    private val mediaSortBySortOrder = mapOf(
        SortOrder.ASCENDING to "ASC",
        SortOrder.DESCENDING to "DESC"
    )

    private val mediaSortBySortBy = mapOf(
        SortBy.TITLE to MediaStore.Audio.Media.TITLE,
        SortBy.ARTIST to MediaStore.Audio.Media.ARTIST,
        SortBy.ALBUM to MediaStore.Audio.Media.ALBUM,
        SortBy.DURATION to MediaStore.Audio.Media.DURATION,
        SortBy.DATE_ADDED to MediaStore.Audio.Media.DATE_ADDED,

        )

}