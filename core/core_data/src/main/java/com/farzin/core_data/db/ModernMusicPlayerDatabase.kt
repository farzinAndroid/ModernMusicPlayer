package com.farzin.core_data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farzin.core_model.db.Playlist
import com.farzin.core_model.db.PlaylistSong

@Database(
    entities = [Playlist::class, PlaylistSong::class],
    version = 1,
    exportSchema = false
)
abstract class ModernMusicPlayerDatabase : RoomDatabase() {

    abstract fun playListDao() : PlaylistDao

}