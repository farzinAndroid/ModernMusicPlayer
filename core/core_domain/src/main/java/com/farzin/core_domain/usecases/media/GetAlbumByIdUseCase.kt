package com.farzin.core_domain.usecases.media

import com.farzin.core_domain.repository.media.MediaRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAlbumByIdUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    operator fun invoke(albumId: Long) =
        mediaRepository.albums.map { list -> list.first { it.id == albumId } }
}