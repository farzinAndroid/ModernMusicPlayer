package com.farzin.home.components.playlists

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farzin.core_model.db.Playlist
import com.farzin.core_model.db.PlaylistSong
import com.farzin.home.home.HomeViewmodel

@Composable
fun Playlists(
    homeViewmodel: HomeViewmodel = hiltViewModel()
) {


    val playLists by homeViewmodel.playlists.collectAsStateWithLifecycle(emptyList())
    val songsInPlaylist by homeViewmodel.songsInPlaylist.collectAsStateWithLifecycle(emptyList())

//    LaunchedEffect(true) {
//        homeViewmodel.createPlaylist(Playlist(
//            name = "test"
//        ))
//    }

    LaunchedEffect(true) {
        homeViewmodel.insertPlaylistSong(listOf(
            PlaylistSong(
                playlistId = 1,
                songId = "1000001056"
            )
        ))
    }


    Log.e("TAG","playlists -> ${playLists.toString()}")
    Log.e("TAG","songsInPlaylist -> ${songsInPlaylist.toString()}")


}