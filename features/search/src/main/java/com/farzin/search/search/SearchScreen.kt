package com.farzin.search.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farzin.core_ui.Screens
import com.farzin.core_ui.common_components.ArtistItem
import com.farzin.core_ui.common_components.FolderItem
import com.farzin.core_ui.common_components.LinearAlbumItem
import com.farzin.core_ui.common_components.SongItem
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.spacing
import com.farzin.player.PlayerViewmodel
import com.farzin.search.components.InitLazyColumn
import com.farzin.search.components.SearchTextField

@Composable
fun SearchScreen(
    navController: NavController,
    searchViewmodel: SearchViewmodel = hiltViewModel(),
    playerViewmodel: PlayerViewmodel = hiltViewModel()
) {

    val uiState by searchViewmodel.uiState.collectAsStateWithLifecycle()
    val musicState by playerViewmodel.musicState.collectAsStateWithLifecycle()



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

                if (state.searchDetails.songs.isNotEmpty()) {
                    InitLazyColumn(
                        headerText = stringResource(com.farzin.core_ui.R.string.songs),
                        list = state.searchDetails.songs,
                        itemContent = { index, song ->
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
                            )
                        }
                    )
                }

                if (state.searchDetails.albums.isNotEmpty()) {
                    InitLazyColumn(
                        headerText = stringResource(com.farzin.core_ui.R.string.albums),
                        list = state.searchDetails.albums,
                        itemContent = { index, album ->
                            LinearAlbumItem(
                                album = album,
                                onClick = {
                                    navController.navigate(Screens.Album(album.id))
                                }
                            )
                        }
                    )
                }

                if (state.searchDetails.artists.isNotEmpty()) {
                    InitLazyColumn(
                        headerText = stringResource(com.farzin.core_ui.R.string.artists),
                        list = state.searchDetails.artists,
                        itemContent = { index, artist->
                            ArtistItem(
                                artist = artist,
                                onClick = {
                                    navController.navigate(Screens.Artist(artist.id))
                                }
                            )
                        }
                    )
                }

                if (state.searchDetails.folders.isNotEmpty()) {
                    InitLazyColumn(
                        headerText = stringResource(com.farzin.core_ui.R.string.folders),
                        list = state.searchDetails.folders,
                        itemContent = { index, folder->
                            FolderItem(
                                folder=folder,
                                onClick = {
                                    navController.navigate(Screens.Folder(folder.name))
                                }
                            )
                        }
                    )
                }

            }

        }
    }
}


