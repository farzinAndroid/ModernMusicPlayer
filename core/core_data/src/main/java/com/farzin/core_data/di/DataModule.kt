package com.farzin.core_data.di

import android.content.Context
import android.content.SharedPreferences
import com.farzin.core_data.domain.usecases.TurnPlayQueueIdToListUseCase
import com.farzin.core_data.preferences.DefaultPreferences
import com.farzin.core_data.repository.SharedPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ) : SharedPreferences= context.getSharedPreferences("shared_preferences",Context.MODE_PRIVATE)



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