package com.farzin.core_model

data class SearchDetails(
    val songs: List<Song>,
    val artists: List<Artist>,
    val albums: List<Album>,
    val folders: List<Folder>
)