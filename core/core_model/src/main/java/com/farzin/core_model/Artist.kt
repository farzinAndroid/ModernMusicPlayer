

package com.farzin.core_model

data class Artist(
    val id: Long = -1L,
    val name: String = "",
    val songs: List<Song> = emptyList()
)
