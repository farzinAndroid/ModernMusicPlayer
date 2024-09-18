package com.farzin.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.NoSongText

@Composable
fun Songs(
    songs: List<Song>,
    currentPlayingSongId: String,
    onClick: (Int) -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {

    if (songs.isNotEmpty()){
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            itemsIndexed(songs, key = {_,song->song.mediaId}){ index, song ->
                SongItem(
                    song = song,
                    onClick = { onClick(index) },
                    onToggleFavorite = onToggleFavorite,
                    isPlaying = song.mediaId == currentPlayingSongId
                )

            }
        }
    }else{
        NoSongText()
    }

}