package com.farzin.core_model.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.farzin.core_common.DbConstants

@Entity(
    tableName = DbConstants.PLAYLIST_SONG_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Playlist::class,
            parentColumns = ["id"],
            childColumns = ["playlistId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlaylistSong(
    val song: SongDB = SongDB(),
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val playlistId: Int = -1,
)
