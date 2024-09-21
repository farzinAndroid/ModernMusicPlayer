package com.farzin.core_model

data class MusicState(
    val currentMediaId: String = "",
    val currentSongIndex: Int =0,
    val playbackState: PlaybackState = PlaybackState.IDLE,
    val playWhenReady: Boolean = false,
    val duration: Long = 0L
)