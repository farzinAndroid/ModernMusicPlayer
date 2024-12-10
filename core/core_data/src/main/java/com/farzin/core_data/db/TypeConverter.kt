package com.farzin.core_data.db

import androidx.room.TypeConverter
import com.farzin.core_model.Song
import com.farzin.core_model.db.SongDB
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    @TypeConverter
    fun fromSong(subtaskList: SongDB): String {
        val json = Gson().toJson(subtaskList)
        return json
    }

    @TypeConverter
    fun toSong(jsonString: String): SongDB {
        val song =
            Gson().fromJson<SongDB>(
                jsonString,
                object : TypeToken<SongDB>() {}.type)
        return song
    }
}