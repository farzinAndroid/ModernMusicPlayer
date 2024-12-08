package com.farzin.home.components.playlists

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farzin.core_model.db.Playlist
import com.farzin.core_ui.common_components.EmptySectionText
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.home.home.HomeViewmodel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Playlists(
    homeViewmodel: HomeViewmodel = hiltViewModel(),
    modifier: Modifier = Modifier,
    playlists: List<Playlist>,
    onPlaylistClicked:(id:Int) ->Unit,
) {
    val songsInPlaylist by homeViewmodel.songsInPlaylist.collectAsStateWithLifecycle(emptyList())

    if (playlists.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
        ) {
            itemsIndexed(playlists, key = { _, song -> song.id }) { index, playlist ->
                Spacer(Modifier.height(MaterialTheme.spacing.small8))
                TextMedium(
                    text = playlist.name,
                    color = MaterialTheme.colorScheme.WhiteDarkBlue,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .clickable{
                            onPlaylistClicked(playlist.id)
                        }
                )

            }
        }
    } else {
        EmptySectionText(stringResource(com.farzin.core_ui.R.string.no_playlists))
    }


}