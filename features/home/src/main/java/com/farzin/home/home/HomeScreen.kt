package com.farzin.home.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.farzin.core_model.PlaybackMode
import com.farzin.core_ui.utils.showToast
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
) {

    val context = LocalContext.current

    val permissionState = rememberPermissionState(
        permission = AudioPermission,
        onPermissionResult = {result->
            if (!result){
                context.showToast(context.getString(com.farzin.core_ui.R.string.grant_permission))
            }
        }
    )

    when(permissionState.status.isGranted){
        true -> {
            Home(homeViewmodel)
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

@Composable
fun Home(homeViewmodel: HomeViewmodel) {
    val userData by homeViewmodel.userData.collectAsState()
    LaunchedEffect(true) {
        homeViewmodel.songs.collectLatest {
            Log.e("TAG",it.toString())
        }
    }

    var playbackMode by remember { mutableStateOf("") }

    LaunchedEffect(homeViewmodel.getUserData()) {
        playbackMode = userData.playbackMode.name
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                homeViewmodel.setPlayBackMode(PlaybackMode.SHUFFLE)
            }
        ) {
            Text(text = userData.playbackMode.name)
        }

    }
}