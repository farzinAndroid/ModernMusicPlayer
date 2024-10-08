package com.farzin.home.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.farzin.core_model.Album
import com.farzin.core_model.Artist
import com.farzin.core_model.Folder
import com.farzin.core_model.Song
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import com.farzin.core_ui.Screens
import com.farzin.core_ui.common_components.Loading
import com.farzin.core_ui.common_components.convertToPosition
import com.farzin.core_ui.common_components.convertToProgress
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.utils.showToast
import com.farzin.home.components.FilterSection
import com.farzin.home.components.HomePager
import com.farzin.home.components.HomeTopBar
import com.farzin.player.player.MiniMusicController
import com.farzin.player.player.FullPlayer
import com.farzin.home.permission.AudioPermission
import com.farzin.home.permission.PermissionScreen
import com.farzin.player.PlayerViewmodel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val permissionState = rememberPermissionState(
        permission = AudioPermission,
        onPermissionResult = { result ->
            if (!result) {
                context.showToast(context.getString(com.farzin.core_ui.R.string.grant_permission))
            }
        }
    )

    when (permissionState.status.isGranted) {
        true -> {
            Home(navController = navController)
        }

        false -> {
            PermissionScreen(
                onButtonClick = {
                    permissionState.launchPermissionRequest()
                }
            )

        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun Home(
    homeViewmodel: HomeViewmodel = hiltViewModel(),
    playerViewmodel: PlayerViewmodel = hiltViewModel(),
    navController: NavController
) {

    val activity = LocalContext.current as Activity

    var showFilter by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
    )
    val sheetState = rememberBottomSheetScaffoldState()
    val isExpanded = when (sheetState.bottomSheetState.targetValue) {
        SheetValue.Hidden -> false
        SheetValue.Expanded -> true
        SheetValue.PartiallyExpanded -> false
    }


    val currentPosition by playerViewmodel.currentPosition.collectAsStateWithLifecycle(0L)
    val musicState by playerViewmodel.musicState.collectAsStateWithLifecycle()
    val playbackMode by playerViewmodel.playbackMode.collectAsStateWithLifecycle()
    val playingQueueSongs by homeViewmodel.playingQueueSongs.collectAsStateWithLifecycle()
    val progress by animateFloatAsState(
        targetValue = convertToProgress(currentPosition, musicState.duration), label = "",
    )


    var loading by remember { mutableStateOf(false) }
    var sortOrder by remember { mutableStateOf(SortOrder.DESCENDING) }
    var sortby by remember { mutableStateOf(SortBy.DATE_ADDED) }
    var songs by remember { mutableStateOf<List<Song>>(emptyList()) }
    var albums by remember { mutableStateOf<List<Album>>(emptyList()) }
    var artists by remember { mutableStateOf<List<Artist>>(emptyList()) }
    var folders by remember { mutableStateOf<List<Folder>>(emptyList()) }
    val homeState by homeViewmodel.homeState.collectAsStateWithLifecycle()
    when (val state = homeState) {
        HomeState.Loading -> {
            loading = true
        }

        is HomeState.Success -> {
            loading = false
            songs = state.songs
            albums = state.albums
            artists = state.artists
            folders = state.folders
            sortby = state.sortBy
            sortOrder = state.sortOrder
        }
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.7f)
                    .background(Color.White)
            ) {
                Text("Hello")
            }
        },
        content = {

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
                                if (songs.isNotEmpty() && playingQueueSongs.isNotEmpty()) {
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




                    if (songs.isNotEmpty() && playingQueueSongs.isNotEmpty()) {
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
                                    activity.moveTaskToBack(true)
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
                            .background(MaterialTheme.colorScheme.BackgroundColor)
                    ) {
                        HomeTopBar(
                            onMenuClicked = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            onFilterClicked = {
                                showFilter = !showFilter
                            },
                            showFilter = showFilter
                        )


                        FilterSection(
                            showFilter = showFilter,
                            sortOrder = sortOrder,
                            sortBy = sortby,
                            onSortOrderClicked = {
                                playerViewmodel.onChangeSortOrder(it)
                            },
                            onSortByClicked = {
                                playerViewmodel.onChangeSortBy(it)
                            }
                        )

                        if (loading) {
                            Loading()
                        } else {
                            HomePager(
                                currentPlayingSongId = musicState.currentMediaId,
                                songs = songs,
                                albums = albums,
                                onSongClick = { index ->
                                    playerViewmodel.play(songs,index)
                                },
                                onAlbumClick = { albumId ->
                                    navController.navigate(Screens.Album(albumId))
                                },
                                musicState = musicState,
                                artists = artists,
                                onArtistClick = {artistId->
                                    navController.navigate(Screens.Artist(artistId))
                                }
                            )
                        }
                    }
                }
            )


        }
    )
}