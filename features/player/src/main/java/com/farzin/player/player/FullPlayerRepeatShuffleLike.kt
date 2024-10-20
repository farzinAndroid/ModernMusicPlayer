package com.farzin.player.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.farzin.core_model.PlaybackMode
import com.farzin.core_ui.theme.DarkGray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun FullPlayerRepeatShuffleLike(
    onToggleLikeButton: (isFavorite:Boolean) -> Unit,
    onPlaybackModeClicked: () -> Unit,
    playbackMode:PlaybackMode,
    isFavorite:Boolean
) {

    val painter = when(playbackMode){
        PlaybackMode.REPEAT-> painterResource(com.farzin.core_ui.R.drawable.ic_repeat)
        PlaybackMode.REPEAT_ONE->painterResource(com.farzin.core_ui.R.drawable.ic_repeat_one)
        PlaybackMode.SHUFFLE->painterResource(com.farzin.core_ui.R.drawable.ic_shuffle)
        else -> null
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.large32),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {

        IconButton(
            onClick = { onToggleLikeButton(!isFavorite) },
            modifier = Modifier
                .size(MaterialTheme.spacing.semiLarge24)
        ) {
            Icon(
                imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                tint = if (!isFavorite) MaterialTheme.colorScheme.DarkGray else MaterialTheme.colorScheme.WhiteDarkBlue

            )
        }



        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.semiMedium12)
        ) {

            IconButton(
                onClick = { onPlaybackModeClicked() },
                modifier = Modifier
                    .size(MaterialTheme.spacing.semiLarge24)
            ) {
                Icon(
                    painter =painter!!,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize(),
                    tint = MaterialTheme.colorScheme.WhiteDarkBlue
                )
            }

        }

    }

}