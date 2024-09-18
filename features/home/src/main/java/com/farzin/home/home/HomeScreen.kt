package com.farzin.home.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.utils.showToast
import com.farzin.home.components.HomeContent
import com.farzin.home.components.HomePager
import com.farzin.home.components.HomeTopBar
import com.farzin.home.permission.AudioPermission
import com.farzin.home.permission.PermissionScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
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
                onSearchClicked = onSearchClicked
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
    onSearchClicked:()->Unit
) {

    /*LaunchedEffect(homeViewmodel.getUserData()) {
        playbackMode = userData.playbackMode.name
    }*/
    val userData by homeViewmodel.userData.collectAsState()
    var playbackMode by remember { mutableStateOf("") }
    val songs by homeViewmodel.songs.collectAsState(emptyList())
    Log.e("TAG","hello ${songs.toString()}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.BackgroundColor)
    ) {
        HomeTopBar(
            onMenuClicked = {},
            onSearchClicked = onSearchClicked
        )

        HomeContent(
            songs = songs,
            onSongClick = {},
            currentPlayingSongId = "",
        )
    }


}