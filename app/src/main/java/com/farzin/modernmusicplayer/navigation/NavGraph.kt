package com.farzin.modernmusicplayer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            )
        }


    }
}