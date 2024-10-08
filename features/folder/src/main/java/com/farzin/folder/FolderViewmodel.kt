package com.farzin.folder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farzin.core_domain.usecases.media.MediaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FolderViewmodel @Inject constructor(
    private val mediaUseCases: MediaUseCases
) : ViewModel() {

    val playingQueueSongs = mediaUseCases.getPlayingQueueSongsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun getFolderByName(folderName:String) = mediaUseCases.getFolderByNameUseCase(folderName)
}