package com.farzin.playlists

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farzin.core_model.Song
import com.farzin.core_model.db.PlaylistSong
import com.farzin.core_model.db.toSong
import com.farzin.core_model.db.toSongDB
import com.farzin.core_ui.common_components.DeleteDialog
import com.farzin.core_ui.common_components.DetailTopBar
import com.farzin.core_ui.common_components.EmptySectionText
import com.farzin.core_ui.common_components.SongItem
import com.farzin.core_ui.common_components.convertToPosition
import com.farzin.core_ui.common_components.convertToProgress
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.spacing
import com.farzin.player.PlayerViewmodel
import com.farzin.player.player.FullPlayer
import com.farzin.player.player.MiniMusicController
import com.farzin.search.search.SearchViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    playlistId: Int,
    playlistName: String,
    playlistViewmodel: PlaylistViewmodel = hiltViewModel(),
    playerViewmodel: PlayerViewmodel = hiltViewModel(),
    searchViewmodel: SearchViewmodel = hiltViewModel(),
    navController: NavController,
) {


    val songsInPlaylist by playlistViewmodel
        .songsInPlaylist(playlistId).collectAsStateWithLifecycle(emptyList())

    var songsToPlay by remember { mutableStateOf<Set<Song>>(emptySet()) }
    var songToDelete by remember { mutableStateOf(PlaylistSong()) }

    LaunchedEffect(songsInPlaylist) {
        songsToPlay = songsInPlaylist.map { it.song.toSong() }.toSet()
        Log.e("TAG", songsToPlay.size.toString())
    }


    val sheetState = rememberBottomSheetScaffoldState()
    val isExpanded = when (sheetState.bottomSheetState.targetValue) {
        SheetValue.Hidden -> false
        SheetValue.Expanded -> true
        SheetValue.PartiallyExpanded -> false
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    val songs by playlistViewmodel.songs.collectAsStateWithLifecycle()
    val currentPosition by playerViewmodel.currentPosition.collectAsStateWithLifecycle(0L)
    val musicState by playerViewmodel.musicState.collectAsStateWithLifecycle()
    val playbackMode by playerViewmodel.playbackMode.collectAsStateWithLifecycle()
    val playingQueueSongs by playlistViewmodel.playingQueueSongs.collectAsStateWithLifecycle()
    val progress by animateFloatAsState(
        targetValue = convertToProgress(currentPosition, musicState.duration), label = "",
    )



    if (playerViewmodel.showDeleteDialog) {
        DeleteDialog(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            onDismiss = {
                playerViewmodel.showDeleteDialog = false
            },
            onConfirm = {
                playlistViewmodel.deleteSongFromPlaylist(songToDelete)
                playerViewmodel.showDeleteDialog = false
            }
        )
    }

    BottomSheetScaffold(
        sheetContent = {
            AnimatedVisibility(
                visible = !isExpanded,
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.BackgroundColor)
                            .clickable {
                                scope.launch {
                                    sheetState.bottomSheetState.expand()
                                }
                            }
                    ) {
                        if (playingQueueSongs.isNotEmpty()) {
                            MiniMusicController(
                                progress = progress,
                                song = playingQueueSongs[musicState.currentSongIndex],
                                onNextClicked = {
                                    playerViewmodel.skipNext()
                                },
                                onPrevClicked = {
                                    playerViewmodel.skipPrevious()
                                },
                                onPlayPauseClicked = {
                                    playerViewmodel.pausePlay(!musicState.playWhenReady)
                                },
                                musicState = musicState,
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }
                    }

                }
            }




            if (playingQueueSongs.isNotEmpty()) {
                FullPlayer(
                    musicState = musicState,
                    songs = playingQueueSongs,
                    onSkipToIndex = {
                        playerViewmodel.skipToIndex(it)
                    },
                    onBackClicked = {
                        if (isExpanded) {
                            scope.launch {
                                sheetState.bottomSheetState.partialExpand()
                            }
                        } else {
                            navController.navigateUp()
                        }
                    },
                    currentPosition = currentPosition,
                    onToggleLikeButton = { id, isFavorite ->
                        playerViewmodel.setFavorite(id, isFavorite)
                    },
                    onPlaybackModeClicked = {
                        playerViewmodel.onTogglePlaybackMode()
                    },
                    onSeekTo = {
                        playerViewmodel.seekTo(convertToPosition(it, musicState.duration))
                    },
                    onPrevClicked = {
                        playerViewmodel.skipPrevious()
                    },
                    onNextClicked = {
                        playerViewmodel.skipNext()
                    },
                    onPlayPauseClicked = {
                        playerViewmodel.pausePlay(!musicState.playWhenReady)
                    },
                    playbackMode = playbackMode
                )
            }

        },
        scaffoldState = sheetState,
        sheetPeekHeight = 64.dp,
        sheetDragHandle = null,
        sheetShape = RoundedCornerShape(0.dp),
        content = {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.BackgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DetailTopBar(
                    onBackClicked = {
                        playlistViewmodel.showAddSongToPlaylistDialog = true
//                        navController.navigateUp()
                    },
                    text = playlistName,
                    isFromAlbumScreen = false
                )

                Spacer(Modifier.height(MaterialTheme.spacing.medium16))

//                AlbumDetailImage(
//                    artworkUri = album?.artworkUri.toString(),
//                    albumName = album?.name ?: ""
//                )

                Spacer(Modifier.height(MaterialTheme.spacing.large32))

                if (songsInPlaylist.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 64.dp)

                    ) {
                        itemsIndexed(songsInPlaylist, key = {_,playlistSong->
                            playlistSong.id
                        }) { index, playlistSong ->
                            Spacer(Modifier.height(MaterialTheme.spacing.small8))
                            SongItem(
                                onClick = {
                                    playerViewmodel.play(
                                        songs = songsToPlay.toList(),
                                        startIndex = index
                                    )
                                },
                                song = playlistSong.song.toSong(),
                                isPlaying = playlistSong.song.mediaId == musicState.currentMediaId,
                                onToggleFavorite = {
                                    playerViewmodel.setFavorite(
                                        playlistSong.song.mediaId,
                                        it
                                    )
                                },
                                isFavorite = playlistSong.song.isFavorite,
                                modifier = Modifier
                                    .animateItem(),
                                onDeleteClicked = {
                                    scope.launch {
                                        songToDelete = PlaylistSong(
                                            song = it.toSongDB(),
                                            playlistId = playlistId
                                        )
                                        // Update songsToPlay immediately after deleting
                                        songsToPlay = songsToPlay.filter { it.mediaId != songToDelete.song.mediaId }.toSet()
                                        playerViewmodel.showDeleteDialog = true
                                    }
                                }
                            )
                        }
                    }
                } else {
                    EmptySectionText(stringResource(com.farzin.core_ui.R.string.no_songs_in_playlist))
                }

            }

            if (playlistViewmodel.showAddSongToPlaylistDialog) {
                AddSongToPlaylistDialog(
                    onDismiss = { playlistViewmodel.showAddSongToPlaylistDialog = false },
                    onConfirm = { playlistSongs ->
                        scope.launch {
                            playlistViewmodel.insertPlaylistSong(
                                playlistSongs
                            )
                            playlistViewmodel.showAddSongToPlaylistDialog = false
                        }
                    },
                    songs = songs,
                    searchViewmodel = searchViewmodel,
                    playlistId = playlistId
                )
            }

        }
    )


}