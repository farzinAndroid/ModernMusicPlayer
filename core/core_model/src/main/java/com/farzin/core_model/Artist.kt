

package com.farzin.core_model

data class Artist(
    val id: Long,
    val name: String,
    val songs: List<Song>
)
