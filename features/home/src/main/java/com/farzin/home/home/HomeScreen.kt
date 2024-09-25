package com.farzin.home.home

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.Loading
import com.farzin.core_ui.common_components.convertToPosition
import com.farzin.core_ui.common_components.convertToProgress
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.utils.showToast
import com.farzin.home.components.HomePager
import com.farzin.home.components.HomeTopBar
import com.farzin.home.components.MiniMusicController
import com.farzin.home.full_player.FullPlayer
import com.farzin.home.permission.AudioPermission
import com.farzin.home.permission.PermissionScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen() {

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
            Home()
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
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    homeViewmodel: HomeViewmodel = hiltViewModel(),
) {

    val activity = LocalContext.current as Activity

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


    val userData by homeViewmodel.userData.collectAsState()
    var playbackMode by remember { mutableStateOf("") }
    LaunchedEffect(homeViewmodel.getUserData()) {
        playbackMode = userData.playbackMode.name
        Log.e("TAG", "Home: $playbackMode")
    }
    val musicState by homeViewmodel.musicState.collectAsStateWithLifecycle()
    val currentPosition by homeViewmodel.currentPosition.collectAsState(0L)
    val progress by animateFloatAsState(
        targetValue = convertToProgress(currentPosition, musicState.duration), label = "",
    )


    var loading by remember { mutableStateOf(false) }
    var songs by remember { mutableStateOf<List<Song>>(emptyList()) }
    val homeState by homeViewmodel.homeState.collectAsStateWithLifecycle()
    when (val state = homeState) {
        HomeState.Loading -> {
            loading = true
        }

        is HomeState.Success -> {
            loading = false
            songs = state.songs
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
                            MiniMusicController(
                                progress = progress,
                                song = songs[musicState.currentSongIndex],
                                onNextClicked = {
                                    homeViewmodel.skipNext()
                                },
                                onPrevClicked = {
                                    homeViewmodel.skipPrevious()
                                },
                                onPlayPauseClicked = {
                                    homeViewmodel.pausePlay(!musicState.playWhenReady)
                                },
                                onMiniMusicControllerClicked = {
                                    scope.launch {
                                        sheetState.bottomSheetState.expand()
                                    }
                                },
                                musicState = musicState
                            )
                        }
                    }




                    FullPlayer(
                        musicState = musicState,
                        songs = songs,
                        onSkipToIndex = {
                            homeViewmodel.skipToIndex(it)
                        },
                        onBackClicked = {
                            if (isExpanded){
                                scope.launch {
                                    sheetState.bottomSheetState.partialExpand()
                                }
                            }
                        },
                        currentPosition = currentPosition,
                        onToggleLikeButton = {},
                        onShuffleClicked = {},
                        onRepeatClicked = {},
                        onSeekTo = {
                            homeViewmodel.seekTo(convertToPosition(it,musicState.duration))
                        }
                    )

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
                            onSearchClicked = {
                                scope.launch {
                                    sheetState.bottomSheetState.expand()
                                }
                            }
                        )

                        if (loading) {
                            Loading()
                        } else {
                            HomePager(
                                currentPlayingSongId = musicState.currentMediaId,
                                songs = songs,
                                onSongClick = { index ->
                                    homeViewmodel.play(
                                        songs,
                                        index
                                    )
                                },
                                musicState = musicState
                            )
                        }
                    }
                }
            )


        }
    )


}