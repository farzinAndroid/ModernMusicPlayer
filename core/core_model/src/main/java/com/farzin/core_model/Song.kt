package com.farzin.core_model

import android.net.Uri
import kotlinx.datetime.LocalDateTime

data class Song(
    val mediaId: String = "",
    val artistId: Long = -1L,
    val albumId: Long = -1L,
    val mediaUri: Uri = Uri.EMPTY,
    val artworkUri: Uri = Uri.EMPTY,
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val duration: Long = -1L,
    val date: LocalDateTime= LocalDateTime(1,2,3,3,2,1),
    val folder:String = ""
)