package com.farzin.core_media_notifications.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.media3.common.Player

internal data class MusicAction(
    @DrawableRes val iconResource: Int,
    @StringRes val titleResource: Int,
    @Player.Command val command: Int
)