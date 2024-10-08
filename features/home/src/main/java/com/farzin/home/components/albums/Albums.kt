package com.farzin.home.components.albums

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.farzin.core_model.Album
import com.farzin.core_ui.common_components.EmptySectionText
import com.farzin.home.R

@Composable
fun Albums(
    albums: List<Album>,
    onClick: (Long) -> Unit,
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
                        onClick(album.id)
                    }
                )
            }
        }
    }else{
        EmptySectionText(stringResource(com.farzin.core_ui.R.string.no_albums))
    }

}