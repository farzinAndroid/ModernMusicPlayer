package com.farzin.folder

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farzin.core_model.Artist
import com.farzin.core_model.Folder
import com.farzin.core_ui.common_components.DetailTopBar
import com.farzin.core_ui.common_components.SongItem
import com.farzin.core_ui.common_components.convertToPosition
import com.farzin.core_ui.common_components.convertToProgress
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.spacing
import com.farzin.player.PlayerViewmodel
import com.farzin.player.player.FullPlayer
import com.farzin.player.player.MiniMusicController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderScreen(
    folderName: String,
    navController: NavController,
    folderViewmodel: FolderViewmodel = hiltViewModel(),
    playerViewmodel: PlayerViewmodel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val currentPosition by playerViewmodel.currentPosition.collectAsStateWithLifecycle(0L)
    val musicState by playerViewmodel.musicState.collectAsStateWithLifecycle()
    val playbackMode by playerViewmodel.playbackMode.collectAsStateWithLifecycle()
    val playingQueueSongs by folderViewmodel.playingQueueSongs.collectAsStateWithLifecycle()
    val progress by animateFloatAsState(
        targetValue = convertToProgress(currentPosition, musicState.duration), label = "",
    )

    var folder by remember { mutableStateOf(Folder()) }
    LaunchedEffect(folderName) {
        folderViewmodel.getFolderByName(folderName).collectLatest {
            folder = it
        }
    }

    val sheetState = rememberBottomSheetScaffoldState()
    val isExpanded = when (sheetState.bottomSheetState.targetValue) {
        SheetValue.Hidden -> false
        SheetValue.Expanded -> true
        SheetValue.PartiallyExpanded -> false
    }

    BottomSheetScaffold(
        sheetContent = {
            AnimatedVisibility(
                visible = !isExpanded,
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(72.dp),
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
                        if (folder.songs.isNotEmpty() && playingQueueSongs.isNotEmpty()) {
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




            if (folder.songs.isNotEmpty() && playingQueueSongs.isNotEmpty()) {
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
                    onToggleLikeButton = {},
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
        sheetPeekHeight = 60.dp,
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
                        navController.navigateUp()
                    },
                    text = folder.name
                )

                Spacer(Modifier.height(MaterialTheme.spacing.medium16))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    itemsIndexed(folder.songs) { index, song ->
                        Spacer(Modifier.height(MaterialTheme.spacing.small8))
                        SongItem(
                            onClick = {
                                playerViewmodel.play(
                                    folder.songs,
                                    index
                                )
                            },
                            song = song,
                            musicState = musicState,
                            onToggleFavorite = {},
                            isPlaying = song.mediaId == musicState.currentMediaId,
                            shouldUseDefaultPic = true
                        )
                    }
                }

            }
        }
    )
}