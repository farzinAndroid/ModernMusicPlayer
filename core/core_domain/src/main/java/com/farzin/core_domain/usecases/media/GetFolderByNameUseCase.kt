package com.farzin.core_domain.usecases.media

import com.farzin.core_domain.repository.MediaRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFolderByNameUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    operator fun invoke(folderName:String) =
        mediaRepository.folders.map { list -> list.first { it.name == folderName } }
}