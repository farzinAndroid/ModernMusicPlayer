package com.farzin.core_data.db

import androidx.room.TypeConverter
import com.farzin.core_model.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    @TypeConverter
    fun fromSong(subtaskList: Song): String {
        val json = Gson().toJson(subtaskList)
        return json
    }

    @TypeConverter
    fun toSong(jsonString: String): Song {
        val song =
            Gson().fromJson<Song>(
                jsonString,
                object : TypeToken<Song>() {}.type)
        return song
    }
}