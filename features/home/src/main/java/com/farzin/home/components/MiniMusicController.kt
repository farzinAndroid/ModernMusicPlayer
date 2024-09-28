package com.farzin.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.farzin.core_model.MusicState
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.ErrorImage
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.common_components.TextRegular
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.DarkGray
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun MiniMusicController(
    progress: Float,
    song: Song,
    musicState: MusicState,
    onPrevClicked:()->Unit,
    onPlayPauseClicked:()->Unit,
    onNextClicked:()->Unit,
    onMiniMusicControllerClicked:()->Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.BackgroundColor)
            .clickable {
                onMiniMusicControllerClicked()
            }
    ) {
        LinearProgressIndicator(
            progress = {
                progress
            },
            modifier = Modifier
                .fillMaxWidth(),
            gapSize = 0.dp,
            strokeCap = StrokeCap.Round,
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            trackColor = MaterialTheme.colorScheme.DarkGray,
            drawStopIndicator = {}
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            SubcomposeAsyncImage(
                model = song.artworkUri,
                contentDescription = "",
                loading = null,
                error = {
                    ErrorImage()
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
            )

            Spacer(Modifier.width(MaterialTheme.spacing.small8))

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.6f)
            ) {
                TextMedium(
                    text = song.title,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.WhiteDarkBlue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = MaterialTheme.spacing.medium16),
                    maxLine = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.width(2.dp))

                TextRegular(
                    text = song.artist,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = MaterialTheme.spacing.medium16),
                    maxLine = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ControllerButton(
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { onPrevClicked() },
                    painter = painterResource(com.farzin.core_ui.R.drawable.prev)
                )


                ControllerButton(
                    modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            onPlayPauseClicked()
                        },
                    painter = if (!musicState.playWhenReady) painterResource(com.farzin.core_ui.R.drawable.ic_play) else
                        painterResource(com.farzin.core_ui.R.drawable.pause)
                )


                ControllerButton(
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { onNextClicked() },
                    painter = painterResource(com.farzin.core_ui.R.drawable.next)
                )
            }
        }
    }

}