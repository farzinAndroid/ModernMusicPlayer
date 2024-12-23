package com.farzin.core_domain.usecases.db

import com.farzin.core_domain.repository.PlaylistRepository
import com.farzin.core_model.db.Playlist
import javax.inject.Inject

class CreatePlaylistUseCase
@Inject constructor(private val playlistRepository: PlaylistRepository) {

    suspend operator fun invoke(playlist: Playlist) = playlistRepository.createPlaylist(playlist)

}