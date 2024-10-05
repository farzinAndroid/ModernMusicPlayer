package com.farzin.core_ui

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object Home : Screens


    @Serializable
    data class Album(val albumId: Long) : Screens



}