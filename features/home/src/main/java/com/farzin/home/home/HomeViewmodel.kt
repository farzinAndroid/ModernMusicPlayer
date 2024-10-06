package com.farzin.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.media.MediaUseCases
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases,
    mediaUseCases: MediaUseCases,
) : ViewModel() {



    val albumById = mediaUseCases.getAlbumByIdUseCase(2265521633247135750).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = Album()
    )

    val playingQueueSongs = mediaUseCases.getPlayingQueueSongsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    private val songs = mediaUseCases.getSongsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    val homeState = combine(
        songs,
        mediaUseCases.getArtistsUseCase(),
        mediaUseCases.getAlbumsUseCase(),
        mediaUseCases.getFoldersUseCase(),
        preferencesUseCases.getUserDataUseCase()
    ) { songs, artists, albums, folders, userData ->
        HomeState.Success(
            songs = songs,
            albums = albums,
            sortOrder = userData.sortOrder,
            sortBy = userData.sortBy
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = HomeState.Loading
    )
}