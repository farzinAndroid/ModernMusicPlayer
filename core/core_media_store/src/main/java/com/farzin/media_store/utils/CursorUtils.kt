package com.farzin.media_store.utils

import android.database.Cursor

object CursorUtils {

    internal fun Cursor.getLong(columnName: String) : Long = this.getLong(getColumnIndexOrThrow(columnName))
    internal fun Cursor.getInt(columnName: String) : Int = this.getInt(getColumnIndexOrThrow(columnName))
    internal fun Cursor.getString(columnName: String) : String = this.getString(getColumnIndexOrThrow(columnName))
}