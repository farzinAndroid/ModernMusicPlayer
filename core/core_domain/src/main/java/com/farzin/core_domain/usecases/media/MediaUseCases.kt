package com.farzin.core_domain.usecases.media

data class MediaUseCases(
    val getSongsUseCase: GetSongsUseCase,
    val getArtistsUseCase: GetArtistUseCase,
    val getAlbumsUseCase: GetAlbumsUseCase,
    val getFoldersUseCase: GetFoldersUseCase,
)