

package com.farzin.core_model

import android.net.Uri

data class Album(
    val id: Long = -1L,
    val artworkUri: Uri = Uri.EMPTY,
    val name: String = "",
    val artist: String = "",
    val songs: List<Song> = emptyList()
)
