

package com.farzin.core_media_service

import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaLibraryService.MediaLibrarySession
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.farzin.core_common.Dispatcher
import com.farzin.core_common.MusicDispatchers
import com.farzin.core_media_service.MusicCommands.PLAYBACK_MODE_REPEAT
import com.farzin.core_media_service.MusicCommands.PLAYBACK_MODE_REPEAT_ONE
import com.farzin.core_media_service.MusicCommands.PLAYBACK_MODE_SHUFFLE
import com.farzin.core_model.PlaybackMode
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject

class MusicSessionCallback @Inject constructor(
    @Dispatcher(MusicDispatchers.MAIN) mainDispatcher: CoroutineDispatcher,
    private val musicActionHandler: MusicActionHandler
) : MediaLibrarySession.Callback {
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())
    val customLayout: List<CommandButton> get() = musicActionHandler.customLayout

    fun setPlaybackModeAction(playbackMode: PlaybackMode) {
        val actionsMap = mapOf(
            PlaybackMode.REPEAT to PLAYBACK_MODE_REPEAT,
            PlaybackMode.REPEAT_ONE to PLAYBACK_MODE_REPEAT_ONE,
            PlaybackMode.SHUFFLE to PLAYBACK_MODE_SHUFFLE
        )
        musicActionHandler.setRepeatShuffleCommand(actionsMap.getValue(playbackMode))
    }


    override fun onAddMediaItems(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItems: List<MediaItem>
    ): ListenableFuture<List<MediaItem>> = Futures.immediateFuture(
        mediaItems.map { mediaItem ->
            mediaItem.buildUpon()
                .setUri(mediaItem.requestMetadata.mediaUri)
                .build()
        }
    )

    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo
    ): MediaSession.ConnectionResult {
        val connectionResult = super.onConnect(session, controller)
        val availableSessionCommands = connectionResult.availableSessionCommands.buildUpon()
        musicActionHandler.customCommands.values.forEach { commandButton ->
            commandButton.sessionCommand?.let{sessionCommand->
                availableSessionCommands.add(sessionCommand)
            }
        }

        return MediaSession.ConnectionResult.accept(
            availableSessionCommands.build(),
            connectionResult.availablePlayerCommands
        )
    }

    override fun onPostConnect(session: MediaSession, controller: MediaSession.ControllerInfo) {
        session.setCustomLayout(controller, musicActionHandler.customLayout)
    }

    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle
    ): ListenableFuture<SessionResult> {
        musicActionHandler.onCustomCommand(mediaSession = session, customCommand = customCommand)
        session.setCustomLayout(musicActionHandler.customLayout)
        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
    }

    fun cancelCoroutineScope() {
        coroutineScope.cancel()
        musicActionHandler.cancelCoroutineScope()
    }
}
