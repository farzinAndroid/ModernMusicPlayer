package com.farzin.core_model.db

import com.farzin.core_model.Song
import kotlinx.datetime.LocalDateTime

data class SongDB(
    val mediaId: String = "",
    val artistId: Long = -1L,
    val albumId: Long = -1L,
    val mediaUri: String = "",
    val artworkUri: String = "",
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val duration: Long = -1L,
    val folder:String = "",
    val isFavorite:Boolean = false
)