package com.farzin.media_store.utils

import android.content.ContentUris
import android.net.Uri

object ArtWorkUtils {

    private const val ALBUM_ART_URI = "content://media/external/audio/albumart"

    internal fun Long.toArtworkUri() = ContentUris.withAppendedId(Uri.parse(ALBUM_ART_URI), this)
}