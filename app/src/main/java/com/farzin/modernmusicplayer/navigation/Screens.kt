package com.farzin.modernmusicplayer.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object Home : Screens


    @Serializable
    data object Permission : Screens



}