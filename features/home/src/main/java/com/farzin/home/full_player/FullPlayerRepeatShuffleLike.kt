package com.farzin.home.full_player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.farzin.core_ui.theme.DarkGray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun FullPlayerRepeatShuffleLike(
    onToggleLikeButton: () -> Unit,
    onPlaybackModeClicked: () -> Unit,
    playbackMode:Int
) {

    val painter = when(playbackMode){
        1-> painterResource(com.farzin.core_ui.R.drawable.ic_repeat)
        2->painterResource(com.farzin.core_ui.R.drawable.ic_repeat_one)
        3->painterResource(com.farzin.core_ui.R.drawable.ic_shuffle)
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
            onClick = { onToggleLikeButton() },
            modifier = Modifier
                .size(MaterialTheme.spacing.semiLarge24)
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                tint = MaterialTheme.colorScheme.DarkGray
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

//            IconButton(
//                onClick = { onShuffleClicked() },
//                modifier = Modifier
//                    .size(MaterialTheme.spacing.semiLarge24)
//            ) {
//                Icon(
//                    painter = painterResource(com.farzin.core_ui.R.drawable.ic_shuffle),
//                    contentDescription = "",
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    tint = if (isShuffleOn)
//                        MaterialTheme.colorScheme.WhiteDarkBlue
//                    else
//                        MaterialTheme.colorScheme.DarkGray
//                )
//            }

        }

    }

}