

package com.farzin.core_datastore.mapper

import com.farzin.core_datastore.PlaybackModeProto
import com.farzin.core_model.PlaybackMode

internal fun PlaybackMode.asPlaybackModeProto() = when (this) {
    PlaybackMode.REPEAT -> PlaybackModeProto.PLAYBACK_MODE_REPEAT
    PlaybackMode.REPEAT_ONE -> PlaybackModeProto.PLAYBACK_MODE_REPEAT_ONE
    PlaybackMode.SHUFFLE -> PlaybackModeProto.PLAYBACK_MODE_SHUFFLE
}

internal fun PlaybackModeProto.asPlaybackMode() = when (this) {
    PlaybackModeProto.UNRECOGNIZED, PlaybackModeProto.PLAYBACK_MODE_REPEAT -> PlaybackMode.REPEAT
    PlaybackModeProto.PLAYBACK_MODE_REPEAT_ONE -> PlaybackMode.REPEAT_ONE
    PlaybackModeProto.PLAYBACK_MODE_SHUFFLE -> PlaybackMode.SHUFFLE
}
