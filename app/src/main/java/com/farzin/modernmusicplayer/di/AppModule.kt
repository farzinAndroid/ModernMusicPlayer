package com.farzin.modernmusicplayer.di

import android.content.Context
import android.content.SharedPreferences
import com.farzin.core_data.domain.usecases.TurnPlayQueueIdToListUseCase
import com.farzin.core_data.preferences.DefaultPreferences
import com.farzin.core_data.repository.MediaRepositoryImpl
import com.farzin.core_data.repository.SettingsRepositoryImpl
import com.farzin.core_datastore.PreferencesDataSource
import com.farzin.core_domain.repository.MediaRepository
import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_domain.usecases.media.GetAlbumsUseCase
import com.farzin.core_domain.usecases.media.GetArtistUseCase
import com.farzin.core_domain.usecases.media.GetFoldersUseCase
import com.farzin.core_domain.usecases.media.GetSongsUseCase
import com.farzin.core_domain.usecases.media.MediaUseCases
import com.farzin.core_domain.usecases.preferences.GetPlaybackModeUseCase
import com.farzin.core_domain.usecases.preferences.GetPlayingQueueIdsUseCase
import com.farzin.core_domain.usecases.preferences.GetPlayingQueueIndexUseCase
import com.farzin.core_domain.usecases.preferences.GetUserDataUseCase
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_domain.usecases.preferences.SetPlaybackModeUseCase
import com.farzin.core_domain.usecases.preferences.SetPlayingQueueIdsUseCase
import com.farzin.core_domain.usecases.preferences.SetPlayingQueueIndexUseCase
import com.farzin.core_domain.usecases.preferences.SetSortByUseCase
import com.farzin.core_domain.usecases.preferences.SetSortOrderUseCase
import com.farzin.media_store.source.MediaStoreSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providePreferencesUseCases(
        defaultPreferences: SharedPreferencesRepository
    ) = PreferencesUseCases(
        getUserDataUseCase = GetUserDataUseCase(defaultPreferences),
        setPlaybackModeUseCase = SetPlaybackModeUseCase(defaultPreferences),
        setSortOrderUseCase = SetSortOrderUseCase(defaultPreferences),
        setSortByUseCase = SetSortByUseCase(defaultPreferences),
        setPlayingQueueIndexUseCase = SetPlayingQueueIndexUseCase(defaultPreferences),
        setPlayingQueueIdsUseCase = SetPlayingQueueIdsUseCase(defaultPreferences),
        getPlaybackModeUseCase = GetPlaybackModeUseCase(defaultPreferences),
        getPlayQueueIndexUseCase = GetPlayingQueueIndexUseCase(defaultPreferences),
        getPlayingQueueIdsUseCase = GetPlayingQueueIdsUseCase(defaultPreferences),
    )

    @Provides
    @Singleton
    fun provideMediaUseCases(
        mediaRepository: MediaRepository
    ) = MediaUseCases(
        getSongsUseCase = GetSongsUseCase(mediaRepository),
        getArtistsUseCase = GetArtistUseCase(mediaRepository),
        getAlbumsUseCase = GetAlbumsUseCase(mediaRepository),
        getFoldersUseCase = GetFoldersUseCase(mediaRepository)
    )

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ) : SharedPreferences = context.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideTurnPlayQueueIdToListUseCase() = TurnPlayQueueIdToListUseCase()


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

}