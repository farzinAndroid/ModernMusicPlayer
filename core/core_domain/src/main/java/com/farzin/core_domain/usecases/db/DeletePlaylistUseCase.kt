package com.farzin.core_domain.usecases.db

import com.farzin.core_domain.repository.db.PlaylistRepository
import com.farzin.core_model.db.Playlist
import javax.inject.Inject

class DeletePlaylistUseCase
@Inject constructor(private val playlistRepository: PlaylistRepository) {

    suspend operator fun invoke(playlist: Playlist) = playlistRepository.deletePlaylist(playlist)

}