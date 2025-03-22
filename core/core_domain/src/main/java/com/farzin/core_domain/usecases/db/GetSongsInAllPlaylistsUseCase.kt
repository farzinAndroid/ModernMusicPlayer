package com.farzin.core_domain.usecases.db

import com.farzin.core_domain.repository.db.PlaylistRepository
import javax.inject.Inject

class GetSongsInAllPlaylistsUseCase
@Inject constructor(private val playlistRepository: PlaylistRepository) {

    operator fun invoke() = playlistRepository.getAllSongsInAllPlaylists()

}