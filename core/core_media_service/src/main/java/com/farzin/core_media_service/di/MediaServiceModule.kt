package com.farzin.core_media_service.di

import android.content.Context
import com.farzin.core_common.Dispatcher
import com.farzin.core_common.MusicDispatchers
import com.farzin.core_domain.usecases.media.GetSongsUseCase
import com.farzin.core_domain.usecases.preferences.PreferencesUseCases
import com.farzin.core_media_service.MusicServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaServiceModule {

    @Provides
    @Singleton
    fun provideMusicServiceConnection(
        @ApplicationContext context: Context,
        preferencesUseCases: PreferencesUseCases,
        getSongsUseCase: GetSongsUseCase,
        @Dispatcher(MusicDispatchers.MAIN) mainDispatcher: CoroutineDispatcher,
    ): MusicServiceConnection = MusicServiceConnection(
        context = context,
        preferencesUseCases = preferencesUseCases,
        getSongsUseCase = getSongsUseCase,
        mainDispatcher = mainDispatcher,
    )
}