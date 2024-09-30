

package com.farzin.core_datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.farzin.core_common.Dispatcher
import com.farzin.core_common.MusicDispatchers
import com.farzin.core_datastore.PreferencesDataSource
import com.farzin.core_datastore.UserPreferences
import com.farzin.core_datastore.serializer.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(MusicDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        userPreferencesSerializer: UserPreferencesSerializer
    ) = DataStoreFactory.create(
        serializer = userPreferencesSerializer,
        scope = CoroutineScope(ioDispatcher + SupervisorJob())
    ) { context.dataStoreFile(USER_PREFERENCES_DATA_STORE_FILE) }


    @Provides
    @Singleton
    fun provideSharedPreferenceDataSource(
        userPreferences: DataStore<UserPreferences>
    ) = PreferencesDataSource(
        userPreferences = userPreferences
    )
}

private const val USER_PREFERENCES_DATA_STORE_FILE = "user_preferences.pb"
