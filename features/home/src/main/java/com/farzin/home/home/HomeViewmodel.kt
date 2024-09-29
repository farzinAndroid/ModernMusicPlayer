package com.farzin.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.media.MediaUseCases
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_media_service.MusicServiceConnection
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.Song
import com.farzin.core_model.SortOrder
import com.farzin.core_model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases,
    private val mediaUseCases: MediaUseCases,
    private val musicServiceConnection: MusicServiceConnection,
) : ViewModel() {

    val musicState = musicServiceConnection.musicState
    val currentPosition = musicServiceConnection.currentPosition

    private val _userData = MutableStateFlow<Flow<UserData>>(emptyFlow())
    val userData: StateFlow<Flow<UserData>> = _userData




    private val _homeState = MutableStateFlow<HomeState>(HomeState.Loading)
    val homeState: StateFlow<HomeState> = _homeState

    init {
        viewModelScope.launch {
            getSongs()
            _userData.value = getUserData()
        }
    }

    fun setPlayBackMode(playbackMode: PlaybackMode){
        viewModelScope.launch {
            preferencesUseCases.setPlaybackModeUseCase(playbackMode)
            musicServiceConnection.setPlaybackMode(playbackMode)
            _userData.value = getUserData()
        }
    }

    fun getUserData(): Flow<UserData> {
        return runBlocking {
            preferencesUseCases.getUserDataUseCase()
        }
    }

    fun setSortOrder(sortOrder: SortOrder){
        viewModelScope.launch {
            preferencesUseCases.setSortOrderUseCase(sortOrder)
            _homeState.update { return@update HomeState.Loading }
            getSongs()
            _userData.value = getUserData()
        }
    }

    private suspend fun getSongs() {
        mediaUseCases.getSongsUseCase().collectLatest { songs ->
            _homeState.update {
                return@update HomeState.Success(songs)
            }
        }
    }

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

    fun setRepeatMode(value:Int){
        viewModelScope.launch {
            preferencesUseCases.setRepeatModeUseCase(value)
        }
    }

    fun getRepeatMode()=
        preferencesUseCases.getRepeatModeUseCase()



}