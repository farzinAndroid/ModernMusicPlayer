package com.farzin.player.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.farzin.core_model.MusicState
import com.farzin.core_ui.common_components.ControllerButton

@Composable
fun FullPlayerMusicController(
    onPrevClicked:()->Unit,
    onNextClicked:()->Unit,
    onPlayPauseClicked:()->Unit,
    musicState: MusicState
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ControllerButton(
            modifier = Modifier
                .size(32.dp)
                .clickable { onPrevClicked() },
            painter = painterResource(com.farzin.core_ui.R.drawable.prev)
        )

        Spacer(Modifier.width(44.dp))

        ControllerButton(
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onPlayPauseClicked()
                },
            painter = if (!musicState.playWhenReady) painterResource(com.farzin.core_ui.R.drawable.ic_play) else
                painterResource(com.farzin.core_ui.R.drawable.pause)
        )

        Spacer(Modifier.width(44.dp))

        ControllerButton(
            modifier = Modifier
                .size(32.dp)
                .clickable { onNextClicked() },
            painter = painterResource(com.farzin.core_ui.R.drawable.next)
        )
    }

}