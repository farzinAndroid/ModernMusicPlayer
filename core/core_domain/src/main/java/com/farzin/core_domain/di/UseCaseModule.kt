package com.farzin.core_domain.di

import com.farzin.core_data.preferences.DefaultPreferences
import com.farzin.core_domain.usecases.GetUserDataUseCase
import com.farzin.core_domain.usecases.PreferencesUseCases
import com.farzin.core_domain.usecases.SetPlaybackModeUseCase
import com.farzin.core_domain.usecases.SetPlayingQueueIdsUseCase
import com.farzin.core_domain.usecases.SetPlayingQueueIndexUseCase
import com.farzin.core_domain.usecases.SetSortByUseCase
import com.farzin.core_domain.usecases.SetSortOrderUseCase
import com.farzin.core_data.domain.usecases.TurnPlayQueueIdToListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideTurnPlayQueueIdToListUseCase() = TurnPlayQueueIdToListUseCase()

    @Provides
    @Singleton fun providePreferencesUseCases(
        defaultPreferences: DefaultPreferences
    ) = PreferencesUseCases(
        getUserDataUseCase = GetUserDataUseCase(defaultPreferences),
        setPlaybackModeUseCase = SetPlaybackModeUseCase(defaultPreferences),
        setSortOrderUseCase = SetSortOrderUseCase(defaultPreferences),
        setSortByUseCase = SetSortByUseCase(defaultPreferences),
        setPlayingQueueIndexUseCase = SetPlayingQueueIndexUseCase(defaultPreferences),
        setPlayingQueueIdsUseCase = SetPlayingQueueIdsUseCase(defaultPreferences),
    )


}