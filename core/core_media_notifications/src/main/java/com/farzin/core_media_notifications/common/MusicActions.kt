package com.farzin.core_media_notifications.common

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import com.farzin.core_media_notifications.R
import com.farzin.core_media_notifications.util.asNotificationAction

@OptIn(UnstableApi::class)
object MusicActions {

    internal fun getRepeatShuffleAction(
        mediaSession: MediaSession,
        customLayout: List<CommandButton>,
        actionFactory: MediaNotification.ActionFactory,
    ) = actionFactory.createCustomActionFromCustomCommandButton(
        mediaSession,
        customLayout.first()
    )


    internal fun skipToPreviousAction(
        mediaSession: MediaSession,
        context: Context,
        actionFactory: MediaNotification.ActionFactory,
    ) = MusicAction(
        iconResource = com.farzin.core_ui.R.drawable.prev,
        titleResource = com.farzin.core_ui.R.string.prev,
        command = Player.COMMAND_SEEK_TO_PREVIOUS
    ).asNotificationAction(context, mediaSession, actionFactory)


    internal fun skipToNextAction(
        mediaSession: MediaSession,
        context: Context,
        actionFactory: MediaNotification.ActionFactory,
    ) = MusicAction(
        iconResource = com.farzin.core_ui.R.drawable.next,
        titleResource = com.farzin.core_ui.R.string.next,
        command = Player.COMMAND_SEEK_TO_NEXT
    ).asNotificationAction(context, mediaSession, actionFactory)


    internal fun playPauseAction(
        mediaSession: MediaSession,
        context: Context,
        actionFactory: MediaNotification.ActionFactory,
        playWhenReady:Boolean
    ) = MusicAction(
        iconResource = if (playWhenReady) com.farzin.core_ui.R.drawable.pause else com.farzin.core_ui.R.drawable.play,
        titleResource = if (playWhenReady) com.farzin.core_ui.R.string.pause else com.farzin.core_ui.R.string.play,
        command = Player.COMMAND_PLAY_PAUSE
    ).asNotificationAction(context, mediaSession, actionFactory)
}