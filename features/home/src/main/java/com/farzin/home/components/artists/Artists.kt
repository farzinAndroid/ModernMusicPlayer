package com.farzin.home.components.artists

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.farzin.core_model.Artist
import com.farzin.core_ui.common_components.EmptySectionText
import com.farzin.core_ui.theme.spacing

@Composable
fun Artists(
    artists: List<Artist>,
    onArtistClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (artists.isNotEmpty()){
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            itemsIndexed(artists, key = { _, artist->artist.id}){ index, artist ->
                Spacer(Modifier.height(MaterialTheme.spacing.small8))
                ArtistItem(
                    artist = artist,
                    onClick = onArtistClick
                )
            }
        }
    }else{
        EmptySectionText(stringResource(com.farzin.core_ui.R.string.no_artist))
    }
}