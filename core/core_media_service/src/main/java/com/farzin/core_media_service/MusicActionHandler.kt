package com.farzin.core_media_service

import android.content.Context
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.farzin.core_common.Dispatcher
import com.farzin.core_common.MusicDispatchers
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_media_notifications.common.MusicCommands.PLAYBACK_MODE
import com.farzin.core_media_notifications.common.MusicCommands.PLAYBACK_MODE_REPEAT
import com.farzin.core_media_notifications.common.MusicCommands.PLAYBACK_MODE_REPEAT_ONE
import com.farzin.core_media_notifications.common.MusicCommands.PLAYBACK_MODE_SHUFFLE
import com.farzin.core_model.PlaybackMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicActionHandler @Inject constructor(
    @Dispatcher(MusicDispatchers.MAIN) mainDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
    private val preferencesUseCases: PreferencesUseCases,
) {
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    val customCommands = getAvailableCustomCommands()
    private val customLayoutMap = mutableMapOf<String, CommandButton>().apply {
        this[PLAYBACK_MODE] = customCommands.getValue(PLAYBACK_MODE_REPEAT)
    }
    val customLayout: List<CommandButton> get() = customLayoutMap.values.toList()

    fun onCustomCommand(mediaSession: MediaSession, customCommand: SessionCommand) {
        when (customCommand.customAction) {
            PLAYBACK_MODE_REPEAT, PLAYBACK_MODE_REPEAT_ONE, PLAYBACK_MODE_SHUFFLE -> {
                handleRepeatShuffleCommand(action = customCommand.customAction)
            }
        }
    }

    fun setRepeatShuffleCommand(action: String) =
        customLayoutMap.set(PLAYBACK_MODE, customCommands.getValue(action))


    fun cancelCoroutineScope() = coroutineScope.cancel()

    private fun handleRepeatShuffleCommand(action: String) = coroutineScope.launch {
        when (action) {
            PLAYBACK_MODE_REPEAT -> preferencesUseCases.setPlaybackModeUseCase(PlaybackMode.REPEAT_ONE)
            PLAYBACK_MODE_REPEAT_ONE -> preferencesUseCases.setPlaybackModeUseCase(PlaybackMode.SHUFFLE)
            PLAYBACK_MODE_SHUFFLE -> preferencesUseCases.setPlaybackModeUseCase(PlaybackMode.REPEAT)
        }
    }


    private fun getAvailableCustomCommands() = mapOf(
        PLAYBACK_MODE_REPEAT to buildCustomCommand(
            action = PLAYBACK_MODE_REPEAT,
            displayName = "PLAYBACK_MODE_REPEAT",
            iconResource = com.farzin.core_ui.R.drawable.ic_repeat
        ),
        PLAYBACK_MODE_REPEAT_ONE to buildCustomCommand(
            action = PLAYBACK_MODE_REPEAT_ONE,
            displayName = "PLAYBACK_MODE_REPEAT_ONE",
            iconResource = com.farzin.core_ui.R.drawable.ic_repeat_one
        ),
        PLAYBACK_MODE_SHUFFLE to buildCustomCommand(
            action = PLAYBACK_MODE_SHUFFLE,
            displayName = "PLAYBACK_MODE_SHUFFLE",
            iconResource = com.farzin.core_ui.R.drawable.ic_shuffle
        )
    )
}

private fun buildCustomCommand(
    action: String,
    displayName: String,
    @DrawableRes iconResource: Int,
) = CommandButton.Builder()
    .setSessionCommand(SessionCommand(action, Bundle.EMPTY))
    .setDisplayName(displayName)
    .setIconResId(iconResource)
    .build()
