package com.farzin.home.home

import com.farzin.core_model.Album
import com.farzin.core_model.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


sealed class HomeState {

    data object Loading : HomeState()
    data class Success(
        val songs: List<Song> = emptyList(),
        val albums:List<Album> = emptyList()
    ) : HomeState()

}