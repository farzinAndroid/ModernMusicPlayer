package com.farzin.core_media_service

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Build
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.farzin.core_common.Dispatcher
import com.farzin.core_common.MusicDispatchers
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_media_notifications.MediaNotificationProvider
import com.farzin.core_media_service.util.unsafeLazy
import com.farzin.core_model.PlaybackMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@AndroidEntryPoint
class MusicService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    @Inject lateinit var musicSessionCallback: MusicSessionCallback
    @Inject lateinit var preferencesUseCases: PreferencesUseCases
    @Inject lateinit var mediaNotificationProvider: MediaNotificationProvider

    private val _currentMediaId = MutableStateFlow("")
    private val currentMediaId = _currentMediaId.asStateFlow()

    @Inject
    @Dispatcher(MusicDispatchers.MAIN)
    lateinit var mainDispatcher: CoroutineDispatcher
    private val coroutineScope by unsafeLazy { CoroutineScope(mainDispatcher + SupervisorJob()) }

    override fun onCreate() {
        super.onCreate()

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(USAGE_MEDIA)
            .build()

        val exoPlayer = ExoPlayer.Builder(this)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build()

        val sessionActivityPendingIntent = TaskStackBuilder.create(this).run {
            addNextIntent(Intent(this@MusicService, Class.forName("com.farzin.modernmusicplayer.MainActivity")))
            val immutableFlag = PendingIntent.FLAG_IMMUTABLE
            getPendingIntent(0, immutableFlag or PendingIntent.FLAG_UPDATE_CURRENT)
        }

        mediaSession = MediaSession.Builder(this, exoPlayer)
            .setCallback(musicSessionCallback)
            .setSessionActivity(sessionActivityPendingIntent)
            .build()
            .apply { exoPlayer.addListener(PlayerListener()) }


        this.setMediaNotificationProvider(mediaNotificationProvider)
        startPlaybackModeSync()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaSession

    @OptIn(UnstableApi::class)
    override fun onDestroy() {
        mediaSession?.run {
            this.player.release()
            this.release()
            clearListener()
            mediaSession = null
        }
        mediaNotificationProvider.cancelCoroutineScope()
        musicSessionCallback.cancelCoroutineScope()
        super.onDestroy()
    }

    private fun startPlaybackModeSync() = coroutineScope.launch {
        val playbackMode = preferencesUseCases.getUserDataUseCase().playbackMode
            mediaSession?.player?.run {
                when (playbackMode) {
                    PlaybackMode.REPEAT -> {
                        this.shuffleModeEnabled = false
                        this.repeatMode = Player.REPEAT_MODE_ALL
                    }

                    PlaybackMode.REPEAT_ONE -> {
                        this.repeatMode = Player.REPEAT_MODE_ONE
                    }

                    PlaybackMode.SHUFFLE -> {
                        this.repeatMode = Player.REPEAT_MODE_ALL
                        this.shuffleModeEnabled = true
                    }
                }
            musicSessionCallback.setPlaybackModeAction(playbackMode)
            mediaSession?.setCustomLayout(musicSessionCallback.customLayout)
        }
    }

    private inner class PlayerListener : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            if (mediaItem == null) return
            _currentMediaId.update { mediaItem.mediaId }
        }
    }
}
