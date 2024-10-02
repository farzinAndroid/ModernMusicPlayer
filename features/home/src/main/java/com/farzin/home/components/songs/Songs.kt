package com.farzin.home.components.songs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.farzin.core_model.MusicState
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.NoSongText
import com.farzin.core_ui.theme.spacing

@Composable
fun Songs(
    songs: List<Song>,
    currentPlayingSongId: String,
    onClick: (Int) -> Unit,
    onToggleFavorite: () -> Unit,
    musicState: MusicState,
    modifier: Modifier = Modifier
) {

    if (songs.isNotEmpty()){
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            itemsIndexed(songs, key = {_,song->song.mediaId}){ index, song ->
                Spacer(Modifier.height(MaterialTheme.spacing.small8))
                SongItem(
                    song = song,
                    onClick = { onClick(index) },
                    onToggleFavorite = onToggleFavorite,
                    isPlaying = song.mediaId == currentPlayingSongId,
                    musicState = musicState
                )

            }
        }
    }else{
        NoSongText()
    }

}