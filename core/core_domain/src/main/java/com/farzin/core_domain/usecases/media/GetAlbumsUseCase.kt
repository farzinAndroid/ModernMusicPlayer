package com.farzin.core_domain.usecases.media

import com.farzin.core_domain.repository.MediaRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    operator fun invoke() = mediaRepository.albums
}