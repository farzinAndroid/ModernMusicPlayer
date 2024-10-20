package com.farzin.core_ui.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.farzin.core_model.Song
import com.farzin.core_ui.R
import com.farzin.core_ui.theme.DarkGray
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun SongItem(
    song: Song,
    isPlaying: Boolean,
    onClick: () -> Unit,
    onToggleFavorite: (isFavorite: Boolean) -> Unit,
    shouldUseDefaultPic: Boolean = false,
    shouldShowPic: Boolean = true,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
) {


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(if (isPlaying) MaterialTheme.colorScheme.Gray else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = MaterialTheme.spacing.medium16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
        ) {
            if (shouldShowPic) {
                if (shouldUseDefaultPic) {
                    Image(
                        painter = painterResource(R.drawable.music_logo),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                } else {
                    SubcomposeAsyncImage(
                        model = song.artworkUri,
                        contentDescription = "",
                        loading = null,
                        error = {
                            ErrorImage()
                        },
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }

            Spacer(Modifier.width(MaterialTheme.spacing.medium16))

            Column(
                horizontalAlignment = Alignment.Start,
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

                Spacer(Modifier.width(MaterialTheme.spacing.extraSmall4))

                TextMedium(
                    text = song.artist,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.DarkGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = MaterialTheme.spacing.medium16),
                    maxLine = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }


        IconButton(
            modifier = Modifier
                .size(MaterialTheme.spacing.semiLarge24),
            onClick = {
                onToggleFavorite(!isFavorite)
            }
        ) {
            Icon(
                imageVector = if (!isFavorite) Icons.Rounded.FavoriteBorder else Icons.Rounded.Favorite,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.WhiteDarkBlue,
            )
        }


    }

}