package com.farzin.home.components.playlists

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farzin.core_model.db.Playlist
import com.farzin.core_ui.common_components.EmptySectionText
import com.farzin.core_ui.common_components.MediaItem
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.home.home.HomeViewmodel

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Playlists(
    homeViewmodel: HomeViewmodel = hiltViewModel(),
    modifier: Modifier = Modifier,
    playlists: List<Playlist>,
    onPlaylistClicked:(id:Int) ->Unit,
) {


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 64.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier
                    .size(56.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.WhiteDarkBlue,
                content = {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.BackgroundColor,
                        modifier = Modifier.size(MaterialTheme.spacing.large32)
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
        content = {
            if (playlists.isNotEmpty()) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(bottom = 64.dp),
                ) {
                    itemsIndexed(playlists, key = { _, song -> song.id }) { index, playlist ->
                        Spacer(Modifier.height(MaterialTheme.spacing.small8))
                        MediaItem(
                            title = playlist.name,
                            subTitle = stringResource(com.farzin.core_ui.R.string.playlist),
                            modifier = Modifier
                                .clickable {
                                    onPlaylistClicked(playlist.id)
                                }
                                .animateItem(),
                            lightModePic = painterResource(com.farzin.core_ui.R.drawable.vinyl_blue),
                            darkModePic = painterResource(com.farzin.core_ui.R.drawable.vinyl_white),
                            shouldHaveMenu = true
                        )

                    }
                }
            } else {
                EmptySectionText(stringResource(com.farzin.core_ui.R.string.no_playlists))
            }
        }
    )




}