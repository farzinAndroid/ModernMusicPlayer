package com.farzin.playlists

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PlaylistsScreen(
    playlistId:Int,
    playlistViewmodel: PlaylistViewmodel = hiltViewModel()
) {

//    val songsInPlaylist by playlistViewmodel.songsInPlaylist.collectAsStateWithLifecycle(emptyList())


    LaunchedEffect(playlistId) {
        playlistViewmodel.getSongsInPlaylist(playlistId)
        playlistViewmodel.songsInPlaylist.collectLatest {
            Log.e("TAG",it.toString())
        }
    }

}