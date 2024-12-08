package com.farzin.core_ui

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object Home : Screens


    @Serializable
    data class Album(val albumId: Long) : Screens


    @Serializable
    data class Artist(val artistId: Long) : Screens


    @Serializable
    data class Folder(val folderName: String) : Screens


    @Serializable
    data object Search : Screens

    @Serializable
    data class Playlists(val playlistId:Int) : Screens



}