package com.farzin.playlists.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.farzin.core_model.db.PlaylistSong
import com.farzin.core_ui.common_components.ErrorImage
import com.farzin.core_ui.theme.spacing

@Composable
fun PlaylistDetailImage(
    songsInPlaylist: List<PlaylistSong>,
    albumName: String,
) {


    Column(
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(MaterialTheme.spacing.medium16))
                .size(240.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {
                SubcomposeAsyncImage(
                    model = if(songsInPlaylist.isNotEmpty()) songsInPlaylist[0].song.artworkUri else "",
                    contentDescription = "",
                    error = {
                        ErrorImage()
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f),
                    contentScale = ContentScale.Crop
                )

                SubcomposeAsyncImage(
                    model = if(songsInPlaylist.size >= 2) songsInPlaylist[1].song.artworkUri else "",
                    contentDescription = "",
                    error = {
                        ErrorImage()
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f),
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {
                SubcomposeAsyncImage(
                    model = if(songsInPlaylist.size >= 3) songsInPlaylist[2].song.artworkUri else "",
                    contentDescription = "",
                    error = {
                        ErrorImage()
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f),
                    contentScale = ContentScale.Crop
                )

                SubcomposeAsyncImage(
                    model = if(songsInPlaylist.size >= 4) songsInPlaylist[3].song.artworkUri else "",
                    contentDescription = "",
                    error = {
                        ErrorImage()
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f),
                    contentScale = ContentScale.Crop
                )
            }
        }

    }


}