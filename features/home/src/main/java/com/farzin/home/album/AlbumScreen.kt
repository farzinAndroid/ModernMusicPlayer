package com.farzin.home.album

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farzin.core_model.Album
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.spacing
import com.farzin.home.components.songs.SongItem
import com.farzin.home.home.HomeViewmodel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AlbumScreen(
    albumId: Long,
    albumViewModel: AlbumViewmodel = hiltViewModel(),
    homeViewmodel: HomeViewmodel = hiltViewModel(),
    navController: NavController
) {

    val musicState by homeViewmodel.musicState.collectAsStateWithLifecycle()

    var album by remember { mutableStateOf(Album()) }
    LaunchedEffect(albumId) {
        albumViewModel.getAlbumById(albumId).collectLatest {
            album = it
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AlbumTopBar(
            onBackClicked = {
                navController.navigateUp()
            },
            albumName = album.name
        )

        Spacer(Modifier.height(MaterialTheme.spacing.semiLarge24))

        AlbumDetailImage(
            artworkUri = album.artworkUri.toString()
        )

        Spacer(Modifier.height(MaterialTheme.spacing.large32))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()

        ) {
            itemsIndexed(album.songs) { index, song ->
                SongItem(
                    onClick = {
                        homeViewmodel.play(
                            album.songs,
                            index
                        )
                    },
                    song = song,
                    musicState = musicState,
                    onToggleFavorite = {},
                    isPlaying = song.mediaId == musicState.currentMediaId
                )
            }
        }

    }


}