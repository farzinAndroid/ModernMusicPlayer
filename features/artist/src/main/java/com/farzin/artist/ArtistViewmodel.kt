package com.farzin.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.media.MediaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArtistViewmodel @Inject constructor(
    private val mediaUseCases: MediaUseCases
) : ViewModel() {

    val playingQueueSongs = mediaUseCases.getPlayingQueueSongsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun getArtistById(albumId: Long) = mediaUseCases.getArtistByIdUseCase(albumId)

}