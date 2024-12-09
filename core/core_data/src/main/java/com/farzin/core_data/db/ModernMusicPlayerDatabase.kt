package com.farzin.core_data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.farzin.core_model.db.Playlist
import com.farzin.core_model.db.PlaylistSong

@Database(
    entities = [Playlist::class, PlaylistSong::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class ModernMusicPlayerDatabase : RoomDatabase() {

    abstract fun playListDao(): PlaylistDao

//    companion object {
//
//        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                // 1. Create a temporary table to store existing data
//                db.execSQL(
//                    """
//            CREATE TABLE PlaylistSong_New (
//                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                song_mediaId TEXT NOT NULL,
//                song_title TEXT NOT NULL,
//                song_artist TEXT NOT NULL,
//                song_album TEXT NOT NULL,
//                song_duration INTEGER NOT NULL,
//                song_uri TEXT NOT NULL,
//                playlistId INTEGER NOT NULL,
//                FOREIGN KEY (playlistId) REFERENCES Playlist(id) ON DELETE CASCADE
//            )
//            """
//                )
//
//                // 2. Copy data from the old table to the new table, transforming songId to Song
//                db.execSQL(
//                    """
//            INSERT INTO PlaylistSong_New (id, song_mediaId, song_title, song_artist, song_album, song_duration, song_uri, playlistId)
//            SELECT ps.id, s.mediaId, s.title, s.artist, s.album, s.duration, s.uri, ps.playlistId
//            FROM PlaylistSong ps
//            INNER JOIN Song s ON ps.songId = s.mediaId
//            """
//                )
//
//                // 3. Drop the old table
//                db.execSQL("DROP TABLE playlist_song")
//
//                // 4. Rename the new table to the original name
//                db.execSQL("ALTER TABLE PlaylistSong_New RENAME TO playlist_song")
//            }
//        }
//
//    }

}