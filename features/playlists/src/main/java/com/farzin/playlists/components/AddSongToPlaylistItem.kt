package com.farzin.playlists.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.common_components.TextRegular
import com.farzin.core_ui.common_components.asFormattedString
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun AddSongToPlaylistItem(
    song: Song,
    onClick: (Song) -> Unit,
    isSelected:Boolean,
    modifier: Modifier = Modifier
) {


    Row(
        modifier = modifier
            .background(if (isSelected) MaterialTheme.colorScheme.Gray else Color.Transparent)
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick(song) }
            .padding(horizontal = MaterialTheme.spacing.medium16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {

        AnimatedVisibility(
            visible = isSelected,
            enter = slideInHorizontally() + expandHorizontally() + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = {100}) + shrinkHorizontally() + fadeOut()
        ) {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = "",
                modifier = Modifier
                    .size(MaterialTheme.spacing.semiLarge24),
                tint = MaterialTheme.colorScheme.WhiteDarkBlue
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = MaterialTheme.spacing.medium16)
        ) {
            TextMedium(
                text = song.title,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.WhiteDarkBlue,
                modifier = Modifier
                    .padding(end = MaterialTheme.spacing.medium16),
                maxLine = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.width(MaterialTheme.spacing.extraSmall4))

            TextMedium(
                text = song.artist,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.WhiteDarkBlue.copy(0.5f),
                modifier = Modifier
                    .padding(end = MaterialTheme.spacing.medium16),
                maxLine = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        TextRegular(
            text = song.duration.asFormattedString(),
            color = MaterialTheme.colorScheme.WhiteDarkBlue.copy(0.5f),
            fontSize = 12.sp,
            textStyle = TextStyle(
                fontWeight = FontWeight.SemiBold
            )
        )



    }


}