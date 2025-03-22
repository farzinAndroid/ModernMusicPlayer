package com.farzin.core_model.remote

data class Lyric(
    val result: List<LyricResult> = emptyList(),
)

data class LyricResult(
    val artist: String = "",
    val copyright: String = "",
    val lyrics: String = "Please wait",
    val track: String = "",
    val written_by: String = "",
)
