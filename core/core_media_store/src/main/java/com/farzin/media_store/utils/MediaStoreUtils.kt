package com.farzin.media_store.utils

import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object MediaStoreUtils {

    /**
     * In essence, this function creates a flow that emits a value whenever
     * the content at the specified URI changes.
     * This allows you to reactively respond to content changes in your application
     * using coroutines.
     */
    internal fun ContentResolver.observe(uri: Uri) = callbackFlow {
        val observer  = object : ContentObserver(null){
            override fun onChange(selfChange: Boolean) {
                trySend(selfChange)
            }
        }
        registerContentObserver(uri,true,observer)
        trySend(true)
        awaitClose {
            unregisterContentObserver(observer)
        }
    }

    val Collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
        MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    }else{
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }

    val Projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.ARTIST_ID,
        MediaStore.Audio.Media.ALBUM_ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.DATE_ADDED,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATA
    )



}