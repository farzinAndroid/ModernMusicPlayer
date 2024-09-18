package com.farzin.home.components

import androidx.compose.runtime.Composable
import com.farzin.core_model.Song

@Composable
fun HomeContent(
    onSongClick: (Int) -> Unit,
    currentPlayingSongId: String,
    songs: List<Song>,
) {
    HomePager(
        currentPlayingSongId = currentPlayingSongId,
        songs = songs,
        onSongClick = onSongClick,
    )
}