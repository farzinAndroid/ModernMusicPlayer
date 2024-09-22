

package com.farzin.core_media_notifications.util

import android.content.Context
import androidx.annotation.OptIn
import androidx.core.graphics.drawable.IconCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import com.farzin.core_media_notifications.common.MusicAction

@OptIn(UnstableApi::class)
internal fun MusicAction.asNotificationAction(
    context: Context,
    mediaSession: MediaSession,
    actionFactory: MediaNotification.ActionFactory,
) = actionFactory.createMediaAction(
    mediaSession,
    IconCompat.createWithResource(context, iconResource),
    context.getString(titleResource),
    this.command
)
