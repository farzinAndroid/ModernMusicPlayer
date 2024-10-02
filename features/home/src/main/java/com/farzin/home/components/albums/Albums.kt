package com.farzin.home.components.albums

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.farzin.core_model.Album
import com.farzin.core_ui.common_components.NoSongText
import com.farzin.core_ui.theme.spacing

@Composable
fun Albums(
    albums: List<Album>,
    onClick: (songIndex:Int) -> Unit,
    albumByID:Album,
    modifier: Modifier = Modifier
) {

    if (albums.isNotEmpty()){
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2)
        ) {
            itemsIndexed(albums, key = { _, album->album.id}){ index, album ->

                AlbumItem(
                    modifier = Modifier,
                    album = album,
                    onAlbumClicked = {
                        onClick(index)
                        Log.e("TAG",album.id.toString())
                    }
                )
            }
        }
    }else{
        NoSongText()
    }

}