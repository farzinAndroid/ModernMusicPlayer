package com.farzin.core_model

object EnumUtils {

    fun fromStringPlaybackMode(value: String): PlaybackMode{
        return when(value){
            "REPEAT" -> PlaybackMode.REPEAT
            "REPEAT_ONE" -> PlaybackMode.REPEAT_ONE
            "SHUFFLE" -> PlaybackMode.SHUFFLE
            else -> PlaybackMode.REPEAT
        }
    }

    fun fromStringSortBy(value: String): SortBy{
        return when(value){
            "TITLE" -> SortBy.TITLE
            "ARTIST" -> SortBy.ARTIST
            "ALBUM" -> SortBy.ALBUM
            "DURATION" -> SortBy.DURATION
            "DATE_ADDED" -> SortBy.DATE_ADDED
            else -> SortBy.TITLE
        }
    }

    fun fromStringSortOrder(value: String): SortOrder{
        return when(value){
            "ASCENDING" -> SortOrder.ASCENDING
            "DESCENDING" -> SortOrder.DESCENDING
            else -> SortOrder.DESCENDING
        }
    }
}