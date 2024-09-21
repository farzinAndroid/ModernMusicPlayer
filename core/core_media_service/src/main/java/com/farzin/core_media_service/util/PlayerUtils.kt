

package com.farzin.core_media_service.util

import androidx.media3.common.C
import androidx.media3.common.Player
import com.farzin.core_model.PlaybackState

internal fun Int.asPlaybackState() = when (this) {
    Player.STATE_IDLE -> PlaybackState.IDLE
    Player.STATE_BUFFERING -> PlaybackState.BUFFERING
    Player.STATE_READY -> PlaybackState.READY
    Player.STATE_ENDED -> PlaybackState.ENDED
    else -> error("Invalid playback state.")
}

internal fun Long.orDefaultTimestamp() = takeIf { it != C.TIME_UNSET } ?: 0L
