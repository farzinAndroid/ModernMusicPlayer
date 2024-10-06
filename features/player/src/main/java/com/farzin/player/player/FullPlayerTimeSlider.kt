package com.farzin.player.player

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_model.MusicState
import com.farzin.core_ui.common_components.TextRegular
import com.farzin.core_ui.common_components.asFormattedString
import com.farzin.core_ui.common_components.convertToProgress
import com.farzin.core_ui.theme.DarkGray
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullPlayerTimeSlider(
    currentPosition: Long,
    musicState: MusicState,
    onSeekTo: (Float) -> Unit,
) {

    val progress by animateFloatAsState(
        targetValue = convertToProgress(currentPosition, musicState.duration),
        label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.large32),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextRegular(
                text = currentPosition.asFormattedString(),
                color = MaterialTheme.colorScheme.Gray,
                fontSize = 12.sp,
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold
                )
            )

            TextRegular(
                text = musicState.duration.asFormattedString(),
                color = MaterialTheme.colorScheme.Gray,
                fontSize = 12.sp,
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Spacer(Modifier.width(MaterialTheme.spacing.large32))

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.large32),
            value = progress,
            onValueChange = onSeekTo,
            track = {
               SliderDefaults.Track(
                   sliderState = it,
                   drawStopIndicator = null,
                   thumbTrackGapSize = 0.dp,
                   modifier = Modifier
                       .height(3.dp),
                   colors = SliderDefaults.colors(
                       activeTrackColor = MaterialTheme.colorScheme.WhiteDarkBlue,
                       inactiveTrackColor = MaterialTheme.colorScheme.DarkGray,
                   )
               )
            },
            thumb = {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(MaterialTheme.spacing.medium16)
                        .background(MaterialTheme.colorScheme.WhiteDarkBlue),
                )
            }
        )

    }

}