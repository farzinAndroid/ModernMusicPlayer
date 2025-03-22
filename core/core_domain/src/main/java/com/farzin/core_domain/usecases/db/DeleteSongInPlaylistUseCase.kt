package com.farzin.core_domain.usecases.db

import com.farzin.core_domain.repository.db.PlaylistRepository
import com.farzin.core_model.db.PlaylistSong
import javax.inject.Inject

class DeleteSongInPlaylistUseCase
@Inject constructor(private val playlistRepository: PlaylistRepository) {

    suspend operator fun invoke(
        playlistSong: PlaylistSong
    ) = playlistRepository.deleteSongInPlaylist(playlistSong)

}