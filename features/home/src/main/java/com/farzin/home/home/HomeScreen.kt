package com.farzin.home.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farzin.core_model.Song
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    homeViewmodel: HomeViewmodel = hiltViewModel(),
    onSearchClicked: () -> Unit
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
                homeViewmodel = homeViewmodel,
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(
    homeViewmodel: HomeViewmodel,
    onSearchClicked:()->Unit,
    onSongClick: (Int) -> Unit,
    currentPlayingSongId: String
) {

    /*LaunchedEffect(homeViewmodel.getUserData()) {
        playbackMode = userData.playbackMode.name
    }*/
    val userData by homeViewmodel.userData.collectAsState()
    var playbackMode by remember { mutableStateOf("") }
    val homeState by homeViewmodel.homeState.collectAsStateWithLifecycle()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.BackgroundColor)
    ) {
        HomeTopBar(
            onMenuClicked = {},
            onSearchClicked = onSearchClicked
        )

        when(val state = homeState){
            HomeState.Loading -> {
                Loading()
            }
            is HomeState.Success -> {
                HomePager(
                    currentPlayingSongId = currentPlayingSongId,
                    songs = state.songs,
                    onSongClick = onSongClick,
                )
            }
        }
    }


}