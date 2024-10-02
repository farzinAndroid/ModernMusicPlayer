package com.farzin.home.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.media.MediaUseCases
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_media_service.MusicServiceConnection
import com.farzin.core_model.Album
import com.farzin.core_model.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    preferencesUseCases: PreferencesUseCases,
    mediaUseCases: MediaUseCases,
    private val musicServiceConnection: MusicServiceConnection,
) : ViewModel() {

    val musicState = musicServiceConnection.musicState
    val currentPosition = musicServiceConnection.currentPosition.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = 0L
    )

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


    fun play(
        songs: List<Song>,
        startIndex: Int = 0,
    ) = musicServiceConnection.playSongs(
        songs, startIndex
    )

    fun pausePlay(isPlaying: Boolean) =
        if (isPlaying) {
            musicServiceConnection.play()
        } else {
            musicServiceConnection.pause()
        }


    fun skipNext() = musicServiceConnection.skipNext()
    fun skipPrevious() = musicServiceConnection.skipPrevious()

    fun skipToIndex(index: Int) = musicServiceConnection.skipToIndex(index)

    fun seekTo(position: Long) = musicServiceConnection.seekTo(position)





}