package com.farzin.modernmusicplayer.di

import android.content.Context
import android.content.SharedPreferences
import com.farzin.core_data.domain.usecases.TurnPlayQueueIdToListUseCase
import com.farzin.core_data.preferences.DefaultPreferences
import com.farzin.core_domain.repository.SharedPreferencesRepository
import com.farzin.core_domain.usecases.GetUserDataUseCase
import com.farzin.core_domain.usecases.PreferencesUseCases
import com.farzin.core_domain.usecases.SetPlaybackModeUseCase
import com.farzin.core_domain.usecases.SetPlayingQueueIdsUseCase
import com.farzin.core_domain.usecases.SetPlayingQueueIndexUseCase
import com.farzin.core_domain.usecases.SetSortByUseCase
import com.farzin.core_domain.usecases.SetSortOrderUseCase
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
    fun provideDefaultPreferences(
        sharedPreferences: SharedPreferences,
        turnPlayQueueIdToListUseCase: TurnPlayQueueIdToListUseCase
    ) : SharedPreferencesRepository = DefaultPreferences(
        sharedPreferences = sharedPreferences,
        turnPlayQueueIdToListUseCase = turnPlayQueueIdToListUseCase
    )

}