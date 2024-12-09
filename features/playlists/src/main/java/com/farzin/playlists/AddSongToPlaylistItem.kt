package com.farzin.playlists

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_model.MusicState
import com.farzin.core_model.Song
import com.farzin.core_ui.R
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.common_components.TextRegular
import com.farzin.core_ui.common_components.asFormattedString
import com.farzin.core_ui.theme.DarkGray
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun AddSongToPlaylistItem(
    song: Song,
    onClick: () -> Unit,
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick() }
            .padding(horizontal = MaterialTheme.spacing.medium16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Spacer(Modifier.width(MaterialTheme.spacing.medium16))

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
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
                color = MaterialTheme.colorScheme.DarkGray,
                modifier = Modifier
                    .padding(end = MaterialTheme.spacing.medium16),
                maxLine = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        TextRegular(
            text = song.duration.asFormattedString(),
            color = MaterialTheme.colorScheme.Gray,
            fontSize = 12.sp,
            textStyle = TextStyle(
                fontWeight = FontWeight.SemiBold
            )
        )



    }


}