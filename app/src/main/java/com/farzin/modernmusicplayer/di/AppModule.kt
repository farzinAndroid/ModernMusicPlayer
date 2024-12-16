package com.farzin.modernmusicplayer.di

import com.farzin.core_data.preferences.SettingsRepositoryImpl
import com.farzin.core_data.repository.MediaRepositoryImpl
import com.farzin.core_datastore.PreferencesDataSource
import com.farzin.core_domain.repository.MediaRepository
import com.farzin.core_domain.repository.PlaylistRepository
import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_domain.usecases.db.CreatePlaylistUseCase
import com.farzin.core_domain.usecases.db.DeletePlaylistUseCase
import com.farzin.core_domain.usecases.db.DeleteSongInPlaylistUseCase
import com.farzin.core_domain.usecases.db.GetAllPlaylistsUseCase
import com.farzin.core_domain.usecases.db.GetSongsInAllPlaylistsUseCase
import com.farzin.core_domain.usecases.db.GetSongsInPlaylistUseCase
import com.farzin.core_domain.usecases.db.InsertPlaylistSongUseCase
import com.farzin.core_domain.usecases.db.PlaylistUseCases
import com.farzin.core_domain.usecases.media.GetAlbumByIdUseCase
import com.farzin.core_domain.usecases.media.GetAlbumsUseCase
import com.farzin.core_domain.usecases.media.GetArtistByIdUseCase
import com.farzin.core_domain.usecases.media.GetArtistUseCase
import com.farzin.core_domain.usecases.media.GetFolderByNameUseCase
import com.farzin.core_domain.usecases.media.GetFoldersUseCase
import com.farzin.core_domain.usecases.media.GetPlayingQueueSongsUseCase
import com.farzin.core_domain.usecases.media.GetRecentlyAddedSongsUseCase
import com.farzin.core_domain.usecases.media.GetSongsUseCase
import com.farzin.core_domain.usecases.media.MediaUseCases
import com.farzin.core_domain.usecases.media.SearchUseCase
import com.farzin.core_domain.usecases.preferences.GetPlaybackModeUseCase
import com.farzin.core_domain.usecases.preferences.GetPlayingQueueIdsUseCase
import com.farzin.core_domain.usecases.preferences.GetPlayingQueueIndexUseCase
import com.farzin.core_domain.usecases.preferences.GetUserDataUseCase
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_domain.usecases.preferences.SetFavoriteUseCase
import com.farzin.core_domain.usecases.preferences.SetPlaybackModeUseCase
import com.farzin.core_domain.usecases.preferences.SetPlayingQueueIdsUseCase
import com.farzin.core_domain.usecases.preferences.SetPlayingQueueIndexUseCase
import com.farzin.core_domain.usecases.preferences.SetSortByUseCase
import com.farzin.core_domain.usecases.preferences.SetSortOrderUseCase
import com.farzin.media_store.source.MediaStoreSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providePreferencesUseCases(
        sharedPreferencesRepository: SharedPreferencesRepository
    ) = PreferencesUseCases(
        getUserDataUseCase = GetUserDataUseCase(sharedPreferencesRepository),
        setPlaybackModeUseCase = SetPlaybackModeUseCase(sharedPreferencesRepository),
        setSortOrderUseCase = SetSortOrderUseCase(sharedPreferencesRepository),
        setSortByUseCase = SetSortByUseCase(sharedPreferencesRepository),
        setPlayingQueueIndexUseCase = SetPlayingQueueIndexUseCase(sharedPreferencesRepository),
        setPlayingQueueIdsUseCase = SetPlayingQueueIdsUseCase(sharedPreferencesRepository),
        getPlaybackModeUseCase = GetPlaybackModeUseCase(sharedPreferencesRepository),
        getPlayQueueIndexUseCase = GetPlayingQueueIndexUseCase(sharedPreferencesRepository),
        getPlayingQueueIdsUseCase = GetPlayingQueueIdsUseCase(sharedPreferencesRepository),
        setFavoriteUseCase = SetFavoriteUseCase(sharedPreferencesRepository)
    )

    @Provides
    @Singleton
    fun provideMediaUseCases(
        mediaRepository: MediaRepository,
        sharedPreferencesRepository: SharedPreferencesRepository
    ) = MediaUseCases(
        getSongsUseCase = GetSongsUseCase(mediaRepository),
        getArtistsUseCase = GetArtistUseCase(mediaRepository),
        getAlbumsUseCase = GetAlbumsUseCase(mediaRepository),
        getFoldersUseCase = GetFoldersUseCase(mediaRepository),
        getPlayingQueueSongsUseCase = GetPlayingQueueSongsUseCase(mediaRepository,sharedPreferencesRepository),
        getAlbumByIdUseCase = GetAlbumByIdUseCase(mediaRepository),
        getArtistByIdUseCase = GetArtistByIdUseCase(mediaRepository),
        getFolderByNameUseCase = GetFolderByNameUseCase(mediaRepository),
        searchUseCase = SearchUseCase(mediaRepository),
        getRecentlyAddedSongsUseCase = GetRecentlyAddedSongsUseCase(mediaRepository)
    )


    @Provides
    @Singleton
    fun provideSettingsRepository(
        preferencesDataSource: PreferencesDataSource
    ) : SharedPreferencesRepository = SettingsRepositoryImpl(
        preferencesDataSource = preferencesDataSource
    )

    @Provides
    @Singleton
    fun provideMediaRepositoryImpl(
        mediaStoreSource: MediaStoreSource,
        preferencesDataSource: PreferencesDataSource
    ) : MediaRepository = MediaRepositoryImpl(
        mediaStoreSource = mediaStoreSource,
        preferencesDataSource = preferencesDataSource
    )

    @Provides
    @Singleton
    fun providePlaylistUseCases(
        playlistRepository: PlaylistRepository
    ) = PlaylistUseCases(
        createPlaylistUseCase = CreatePlaylistUseCase(playlistRepository),
        getAllPlaylistsUseCase = GetAllPlaylistsUseCase(playlistRepository),
        getSongsInPlaylistUseCase = GetSongsInPlaylistUseCase(playlistRepository),
        insertPlaylistSongUseCase = InsertPlaylistSongUseCase(playlistRepository),
        deletePlaylistUseCase = DeletePlaylistUseCase(playlistRepository),
        deleteSongInPlaylistUseCase = DeleteSongInPlaylistUseCase(playlistRepository),
        getSongsInAllPlaylistsUseCase = GetSongsInAllPlaylistsUseCase(playlistRepository)
    )

}