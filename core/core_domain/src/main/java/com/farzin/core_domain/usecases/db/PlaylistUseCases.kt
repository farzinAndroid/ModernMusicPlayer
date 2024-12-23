package com.farzin.core_domain.usecases.db

data class PlaylistUseCases(
    val createPlaylistUseCase: CreatePlaylistUseCase,
    val getAllPlaylistsUseCase: GetAllPlaylistsUseCase,
    val getSongsInPlaylistUseCase: GetSongsInPlaylistUseCase,
    val insertPlaylistSongUseCase: InsertPlaylistSongUseCase,
    val deletePlaylistUseCase: DeletePlaylistUseCase,
    val deleteSongInPlaylistUseCase: DeleteSongInPlaylistUseCase,
    val getSongsInAllPlaylistsUseCase: GetSongsInAllPlaylistsUseCase
)