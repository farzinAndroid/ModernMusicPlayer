package com.farzin.core_data.db

import android.content.Context
import androidx.room.Room
import com.farzin.core_common.DbConstants
import com.farzin.core_domain.repository.PlaylistRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDbModule {


    @Provides
    @Singleton
    fun provideModernMusicPlayerDatabase(
        @ApplicationContext context:Context
    ) = Room
        .databaseBuilder(context,ModernMusicPlayerDatabase::class.java, DbConstants.DBNAME)
        .build()

    @Provides
    @Singleton
    fun providePlayListDao(
        modernMusicPlayerDatabase: ModernMusicPlayerDatabase
    ) : PlaylistRepository = modernMusicPlayerDatabase.playListDao()

}