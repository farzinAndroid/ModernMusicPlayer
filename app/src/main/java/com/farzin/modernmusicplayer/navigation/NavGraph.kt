package com.farzin.modernmusicplayer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.farzin.album.AlbumScreen
import com.farzin.artist.ArtistScreen
import com.farzin.core_ui.Screens
import com.farzin.folder.FolderScreen
import com.farzin.home.home.HomeScreen
import com.farzin.playlists.PlaylistsScreen
import com.farzin.search.search.SearchScreen

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
            AlbumScreen(
                albumId = args.albumId,
                navController = navHostController
            )
        }


        composable<Screens.Artist> {
            val args = it.toRoute<Screens.Artist>()
            ArtistScreen(
                artistId = args.artistId,
                navController = navHostController
            )
        }



        composable<Screens.Folder> {
            val args = it.toRoute<Screens.Folder>()
            FolderScreen(
                folderName = args.folderName,
                navController = navHostController
            )
        }


        composable<Screens.Search> {
            SearchScreen(
                navController = navHostController
            )
        }

        composable<Screens.Playlists> {
            val args = it.toRoute<Screens.Playlists>()
            PlaylistsScreen(
                playlistId = args.playlistId,
                playlistName = args.playlistName,
                navController = navHostController
            )
        }


    }
}