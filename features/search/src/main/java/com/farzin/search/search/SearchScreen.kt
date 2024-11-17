package com.farzin.search.search

import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farzin.core_model.Song
import com.farzin.core_ui.Screens
import com.farzin.core_ui.common_components.ArtistItem
import com.farzin.core_ui.common_components.FolderItem
import com.farzin.core_ui.common_components.LinearAlbumItem
import com.farzin.core_ui.common_components.SongItem
import com.farzin.core_ui.common_components.TextBold
import com.farzin.core_ui.common_components.deleteLauncher
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.core_ui.utils.showToast
import com.farzin.player.PlayerViewmodel
import com.farzin.search.R
import com.farzin.search.components.HeaderText
import com.farzin.search.components.SearchTextField
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(
    navController: NavController,
    searchViewmodel: SearchViewmodel = hiltViewModel(),
    playerViewmodel: PlayerViewmodel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val uiState by searchViewmodel.uiState.collectAsStateWithLifecycle()
    val musicState by playerViewmodel.musicState.collectAsStateWithLifecycle()


    var songToDelete by remember { mutableStateOf(Song()) }
    val context = LocalContext.current
    val launcher = deleteLauncher(songToDelete)

    when (val state = uiState) {
        SearchUiState.Loading -> {}
        is SearchUiState.Success -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.BackgroundColor)
                    .statusBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchTextField(
                    onValueChange = { searchViewmodel.changeQuery(it) },
                    onClearClicked = { searchViewmodel.clear() },
                    value = state.query
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 64.dp)
                ) {
                    if (state.searchDetails.songs.isNotEmpty()){
                        item {
                            HeaderText(stringResource(com.farzin.core_ui.R.string.songs))
                        }

                        itemsIndexed(state.searchDetails.songs){index, song ->
                            Spacer(Modifier.height(MaterialTheme.spacing.small8))
                            SongItem(
                                song = song,
                                onClick = {
                                    playerViewmodel.play(
                                        songs = state.searchDetails.songs,
                                        startIndex = index
                                    )
                                },
                                isPlaying = false,
                                onToggleFavorite = {playerViewmodel.setFavorite(song.mediaId, it)},
                                isFavorite = song.isFavorite,
                                modifier =Modifier
                                    .animateItem(),
                                onDeleteClicked = {
                                    songToDelete = it
                                    playerViewmodel.deleteSong(songToDelete,launcher)
                                }
                            )
                        }
                    }

                    if (state.searchDetails.albums.isNotEmpty()){
                        item {
                            HeaderText(stringResource(com.farzin.core_ui.R.string.albums))
                        }

                        itemsIndexed(state.searchDetails.albums){index, album ->
                            Spacer(Modifier.height(MaterialTheme.spacing.small8))
                            LinearAlbumItem(
                                album = album,
                                onClick = {
                                    navController.navigate(Screens.Album(album.id))
                                },
                                modifier =Modifier
                                    .animateItem()
                            )
                        }
                    }

                    if (state.searchDetails.artists.isNotEmpty()){
                        item {
                            HeaderText(stringResource(com.farzin.core_ui.R.string.artists))
                        }

                        itemsIndexed(state.searchDetails.artists){index, artist ->
                            Spacer(Modifier.height(MaterialTheme.spacing.small8))
                            ArtistItem(
                                artist = artist,
                                onClick = {
                                    navController.navigate(Screens.Artist(artist.id))
                                },
                                modifier =Modifier
                                    .animateItem()
                            )
                        }
                    }

                    if (state.searchDetails.folders.isNotEmpty()){
                        item {
                            HeaderText(stringResource(com.farzin.core_ui.R.string.folders))
                        }

                        itemsIndexed(state.searchDetails.folders){index, folder ->
                            Spacer(Modifier.height(MaterialTheme.spacing.small8))
                            FolderItem(
                                folder=folder,
                                onClick = {
                                    navController.navigate(Screens.Folder(folder.name))
                                },
                                modifier =Modifier
                                    .animateItem()
                            )
                        }
                    }
                }

            }

        }
    }
}


