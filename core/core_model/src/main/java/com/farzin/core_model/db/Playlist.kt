package com.farzin.core_model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.farzin.core_common.DbConstants


@Entity(DbConstants.PLAYLIST_TABLE_NAME)
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String = ""
)
