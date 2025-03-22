package com.farzin.core_domain.usecases.media

import com.farzin.core_domain.repository.media.MediaRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetArtistByIdUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    operator fun invoke(artistId: Long) =
        mediaRepository.artists.map { list -> list.first { it.id == artistId } }
}