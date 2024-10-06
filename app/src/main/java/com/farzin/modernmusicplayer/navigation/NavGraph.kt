package com.farzin.modernmusicplayer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.farzin.core_ui.Screens
import com.farzin.album.AlbumScreen
import com.farzin.home.home.HomeScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = Screens.Home
    ) {

        composable<Screens.Home> {
            HomeScreen(
                navController = navHostController
            )
        }

        composable<Screens.Album> {
            val args = it.toRoute<Screens.Album>()
            com.farzin.album.AlbumScreen(
                albumId = args.albumId,
                navController = navHostController
            )
        }


    }
}