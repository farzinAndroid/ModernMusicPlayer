package com.farzin.home.components.recently_added

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.EmptySectionText
import com.farzin.core_ui.common_components.SongItem
import com.farzin.core_ui.theme.spacing

@Composable
fun RecentlyAdded(
    recentSongs: List<Song>,
    currentPlayingSongId: String,
    onClick: (Int,List<Song>) -> Unit,
    onToggleFavorite: (id:String,isFavorite:Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    if (recentSongs.isNotEmpty()){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
        ) {
            itemsIndexed(recentSongs, key = { _, song->song.mediaId}){ index, song ->
                Spacer(Modifier.height(MaterialTheme.spacing.small8))
                SongItem(
                    song = song,
                    onClick = { onClick(index,recentSongs) },
                    onToggleFavorite = { onToggleFavorite(song.mediaId,it) },
                    isPlaying = song.mediaId == currentPlayingSongId,
                    isFavorite = song.isFavorite,
                    modifier =Modifier
                        .animateItem()
                )

            }
        }
    }else{
        EmptySectionText(stringResource(com.farzin.core_ui.R.string.no_recently_added_songs))
    }

}