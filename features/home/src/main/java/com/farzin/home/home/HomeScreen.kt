package com.farzin.home.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.SheetState
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farzin.core_ui.common_components.Loading
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.utils.showToast
import com.farzin.home.components.HomePager
import com.farzin.home.components.HomeTopBar
import com.farzin.home.permission.AudioPermission
import com.farzin.home.permission.PermissionScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    onSearchClicked: () -> Unit,
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
            Home(
                onSearchClicked = onSearchClicked,
                onSongClick = {},
                currentPlayingSongId = ""
            )
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
    onSearchClicked: () -> Unit,
    onSongClick: (Int) -> Unit,
    currentPlayingSongId: String,
) {



    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
    )
    val sheetState = rememberBottomSheetScaffoldState()
    var isExpanded = when (sheetState.bottomSheetState.targetValue) {
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
    val homeState by homeViewmodel.homeState.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
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
                                .height(70.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                                    .clickable {
                                        scope.launch {
                                            sheetState.bottomSheetState.expand()
                                        }
                                    }
                            ) {
                                Text("Hello")
                            }
                        }
                    }



                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(Color.Black)

                    ) {
                        Text("Hello")
                    }

                },
                scaffoldState =sheetState,
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

                        when (val state = homeState) {
                            HomeState.Loading -> {
                                Loading()
                            }

                            is HomeState.Success -> {
                                HomePager(
                                    currentPlayingSongId = currentPlayingSongId,
                                    songs = state.songs,
                                    onSongClick = {index->
                                        homeViewmodel.play(
                                            state.songs,
                                            index
                                        )
                                    },
                                )
                            }
                        }
                    }
                }
            )



        }
    )




}