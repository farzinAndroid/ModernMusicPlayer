package com.farzin.core_ui.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
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
    onDeleteClicked: (song: Song) -> Unit,
    modifier: Modifier = Modifier,
) {

    var isMenuExpanded by remember { mutableStateOf(false) }

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
            onClick = { isMenuExpanded = !isMenuExpanded },
            modifier = Modifier
                .size(26.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                tint = MaterialTheme.colorScheme.WhiteDarkBlue
            )
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            modifier = Modifier,
            offset = DpOffset(LocalConfiguration.current.screenWidthDp.dp, 0.dp),
            containerColor = Color.White,
        ) {
            DropdownMenuItem(
                text = {
                    TextMedium(
                        text = stringResource(com.farzin.core_ui.R.string.delete),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                },
                onClick = {
                    onDeleteClicked(song)
                    isMenuExpanded = false
                },
                modifier = Modifier
                    .height(40.dp)
            )

            DropdownMenuItem(
                text = {
                    TextMedium(
                        text = if (!isFavorite) stringResource(com.farzin.core_ui.R.string.add_to_fav) else stringResource(
                            com.farzin.core_ui.R.string.remove_from_fav
                        ),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                },
                onClick = {
                    onToggleFavorite(!isFavorite)
                    isMenuExpanded = false
                },
                modifier = Modifier
                    .height(40.dp),
                trailingIcon = {
                    Icon(
                        imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                        contentDescription = "",
                        tint = if (!isFavorite) MaterialTheme.colorScheme.DarkGray
                        else MaterialTheme.colorScheme.WhiteDarkBlue
                    )
                }
            )

        }


    }

}