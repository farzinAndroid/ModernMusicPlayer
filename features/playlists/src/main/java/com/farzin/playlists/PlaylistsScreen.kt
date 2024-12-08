package com.farzin.playlists

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlaylistsScreen(
    playlistId:Int
) {

    Text(playlistId.toString())

}