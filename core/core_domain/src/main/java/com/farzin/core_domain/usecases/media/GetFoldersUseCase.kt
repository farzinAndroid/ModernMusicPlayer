package com.farzin.core_domain.usecases.media

import com.farzin.core_domain.repository.media.MediaRepository
import javax.inject.Inject

class GetFoldersUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    operator fun invoke() = mediaRepository.folders
}