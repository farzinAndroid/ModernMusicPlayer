package com.farzin.home.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.media.MediaUseCases
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_media_service.MusicServiceConnection
import com.farzin.core_model.Album
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.SortBy
import com.farzin.core_model.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlbumViewmodel @Inject constructor(
    private val mediaUseCases: MediaUseCases,
    private val preferencesUseCases: PreferencesUseCases
) : ViewModel() {


    fun getAlbumById(albumId: Long) = mediaUseCases.getAlbumByIdUseCase(albumId)

}