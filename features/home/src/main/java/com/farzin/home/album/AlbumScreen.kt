package com.farzin.home.album

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.farzin.core_model.Album
import com.farzin.core_ui.Screens
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.home.home.HomeViewmodel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AlbumScreen(
    albumId:Long,
    albumViewModel: AlbumViewmodel = hiltViewModel(),
    homeViewmodel: HomeViewmodel = hiltViewModel()
) {


    var album by remember { mutableStateOf(Album()) }
    LaunchedEffect(albumId) {
        albumViewModel.getAlbumById(albumId).collectLatest {
            album = it
        }
    }

    var scope = rememberCoroutineScope()



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.BackgroundColor)
    ) {
        itemsIndexed(album.songs){index, song ->
            Text(
                text = song.title,
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            albumViewModel.setPlayingQueue(album)
                            delay(1000)
                            homeViewmodel.play(album.songs,index)
                        }
                    }
            )
        }
    }

}