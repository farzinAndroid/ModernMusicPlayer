

package com.farzin.core_media_service

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.common.Player.EVENT_MEDIA_ITEM_TRANSITION
import androidx.media3.common.Player.EVENT_MEDIA_METADATA_CHANGED
import androidx.media3.common.Player.EVENT_PLAYBACK_STATE_CHANGED
import androidx.media3.common.Player.EVENT_PLAY_WHEN_READY_CHANGED
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.farzin.core_common.Dispatcher
import com.farzin.core_common.MusicDispatchers
import com.farzin.core_domain.usecases.media.GetSongsUseCase
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_media_service.mapper.asMediaItem
import com.farzin.core_media_service.util.asPlaybackState
import com.farzin.core_media_service.util.orDefaultTimestamp
import com.farzin.core_model.MusicState
import com.farzin.core_model.Song
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds


class MusicServiceConnection @Inject constructor(
    @ApplicationContext context: Context,
    @Dispatcher(MusicDispatchers.MAIN) mainDispatcher: CoroutineDispatcher,
    private val getSongsUseCase: GetSongsUseCase,
    private val preferencesUseCases: PreferencesUseCases,
) {
    private var mediaController: MediaController? = null
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    private val _musicState = MutableStateFlow(MusicState())
    val musicState = _musicState.asStateFlow()

    val currentPosition = flow {
        while (currentCoroutineContext().isActive) {
            val currentPosition = mediaController?.currentPosition ?: 0L
            emit(currentPosition)
            delay(1.milliseconds)
        }
    }

    init {
        coroutineScope.launch {
            mediaController = MediaController.Builder(
                context,
                SessionToken(context, ComponentName(context, MusicService::class.java))
            ).buildAsync().await().apply { addListener(PlayerListener()) }
            updatePlayingQueue()
        }
    }

    fun skipPrevious() = mediaController?.run {
        seekToPrevious()
        play()
    }

    fun play() = mediaController?.play()
    fun pause() = mediaController?.pause()

    fun skipNext() = mediaController?.run {
        seekToNext()
        play()
    }

    fun skipTo(position: Long) = mediaController?.run {
        seekTo(position)
        play()
    }

    fun skipToIndex(index: Int, position: Long = 0L) = mediaController?.run {
        seekTo(index, position)
        play()
    }

    fun playSongs(
        songs: List<Song>,
        startIndex: Int = 0,
        startPositionMs: Long = 0L,
    ) {
        mediaController?.run {
            setMediaItems(songs.map(Song::asMediaItem), startIndex, startPositionMs)
            prepare()
            play()
        }
        coroutineScope.launch {
            preferencesUseCases.setPlayingQueueIdsUseCase(
                playingQueueIds = songs.map(
                    Song::mediaId
                )
            )
        }
    }

    fun shuffleSongs(
        songs: List<Song>,
        startIndex: Int = 0,
        startPositionMs: Long = 0L,
    ) = playSongs(
        songs = songs.shuffled(),
        startIndex = startIndex,
        startPositionMs = startPositionMs
    )

    private inner class PlayerListener : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            if (events.containsAny(
                    EVENT_PLAYBACK_STATE_CHANGED,
                    EVENT_MEDIA_METADATA_CHANGED,
                    EVENT_PLAY_WHEN_READY_CHANGED
                )
            ) {
                updateMusicState(player)
            }

            if (events.contains(EVENT_MEDIA_ITEM_TRANSITION)) {
                updatePlayingQueueIndex(player)
            }
        }
    }

    private fun updateMusicState(player: Player) = with(player) {
        _musicState.update {
            it.copy(
                currentMediaId = currentMediaItem?.mediaId.orEmpty(),
                playbackState = playbackState.asPlaybackState(),
                playWhenReady = playWhenReady,
                duration = duration.orDefaultTimestamp()
            )
        }
    }

    private suspend fun updatePlayingQueue(startPositionMs: Long = 0L) {
        val songs = getSongsUseCase().first()
        if (songs.isEmpty()) return

        val playingQueueSongs =
            preferencesUseCases.getUserDataUseCase().playingQueueIds.map { playingQueueId ->
                songs.find { it.mediaId == playingQueueId }
            }.ifEmpty {
                preferencesUseCases.setPlayingQueueIdsUseCase(playingQueueIds = songs.map(Song::mediaId))
                songs
            }

        val startIndex =
            preferencesUseCases.getUserDataUseCase().playingQueueIndex.let { startIndex ->
                if (startIndex < playingQueueSongs.size) {
                    startIndex
                } else {
                    preferencesUseCases.setPlayingQueueIndexUseCase(0)
                    0
                }
            }

        mediaController?.run {
            setMediaItems(playingQueueSongs.map { it!!.asMediaItem() }, startIndex, startPositionMs)
            prepare()
        }
    }

    private fun updatePlayingQueueIndex(player: Player) {
        val index = player.currentMediaItemIndex
        _musicState.update { it.copy(currentSongIndex = index) }
        coroutineScope.launch { preferencesUseCases.setPlayingQueueIndexUseCase(index) }
    }
}
