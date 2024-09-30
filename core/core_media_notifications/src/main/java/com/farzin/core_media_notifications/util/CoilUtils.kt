

package com.farzin.core_media_notifications.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest

internal suspend fun Uri.asArtworkBitmap(context: Context): Bitmap? {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(this)
        .placeholder(com.farzin.core_ui.R.drawable.music_logo)
        .error(com.farzin.core_ui.R.drawable.music_logo)
        .build()

    val drawable = loader.execute(request).drawable
    return drawable?.toBitmap()
}
