package com.farzin.core_media_notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaStyleNotificationHelper
import com.farzin.core_common.Dispatcher
import com.farzin.core_common.MusicDispatchers
import com.farzin.core_media_notifications.common.MusicActions
import com.farzin.core_media_notifications.util.asArtworkBitmap
import com.google.common.collect.ImmutableList
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@UnstableApi
class MediaNotificationProvider @Inject constructor(
    @Dispatcher(MusicDispatchers.MAIN) mainDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    @Dispatcher(MusicDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val notificationManager: NotificationManager,
) : MediaNotification.Provider {

    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    override fun createNotification(
        mediaSession: MediaSession,
        customLayout: ImmutableList<CommandButton>,
        actionFactory: MediaNotification.ActionFactory,
        onNotificationChangedCallback: MediaNotification.Provider.Callback,
    ): MediaNotification {
        createNotificationChannel()

        val player = mediaSession.player
        val metaData = player.mediaMetadata


        val notificationBuilder = NotificationCompat.Builder(context, MusicNotificationChannelId)
            .setContentTitle(metaData.title)
            .setContentText(metaData.artist)
            .setSmallIcon(com.farzin.core_ui.R.drawable.music)
            .setStyle(MediaStyleNotificationHelper.MediaStyle(mediaSession))
            .setContentIntent(mediaSession.sessionActivity)

        getNotificationActions(
            mediaSession = mediaSession,
            customLayout = customLayout,
            actionFactory = actionFactory,
            playWhenReady = player.playWhenReady
        ).forEach { notificationAction ->
            notificationBuilder.addAction(notificationAction)
        }

        setupArtwork(
            uri = metaData.artworkUri,
            setLargeIcon = { notificationBuilder.setLargeIcon(it) },
            updateNotification = {
                val notification =
                    MediaNotification(MusicNotificationId, notificationBuilder.build())
                onNotificationChangedCallback.onNotificationChanged(notification)
            }
        )

        return MediaNotification(MusicNotificationId, notificationBuilder.build())

    }


    private fun getNotificationActions(
        mediaSession: MediaSession,
        customLayout: List<CommandButton>,
        actionFactory: MediaNotification.ActionFactory,
        playWhenReady: Boolean,
    ) = listOf(
//        MusicActions.getRepeatShuffleAction(mediaSession, customLayout, actionFactory),
        MusicActions.skipToPreviousAction(mediaSession, context, actionFactory),
        MusicActions.playPauseAction(mediaSession, context, actionFactory, playWhenReady),
        MusicActions.skipToNextAction(mediaSession, context, actionFactory),
    )

    override fun handleCustomCommand(
        session: MediaSession,
        action: String,
        extras: Bundle,
    ) = false

    private fun createNotificationChannel() {
        if (
            notificationManager.getNotificationChannel(MusicNotificationChannelId) != null
        ) {
            return
        }

        val notificationChannel = NotificationChannel(
            MusicNotificationChannelId,
            context.getString(com.farzin.core_ui.R.string.playing),
            NotificationManager.IMPORTANCE_LOW
        )

        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun cancelCoroutineScope() = coroutineScope.cancel()

    private fun setupArtwork(
        uri: Uri?,
        setLargeIcon: (Bitmap?) -> Unit,
        updateNotification: () -> Unit,
    ) = coroutineScope.launch {
        val bitmap = loadArtworkBitmap(uri)
        setLargeIcon(bitmap)
        updateNotification()
    }

    private suspend fun loadArtworkBitmap(uri: Uri?) =
        withContext(ioDispatcher) { uri?.asArtworkBitmap(context) }
}

private const val MusicNotificationId = 10
private const val MusicNotificationChannelId = "MusicNotificationChannel"