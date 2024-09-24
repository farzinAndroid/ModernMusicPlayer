package com.farzin.core_ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {

    suspend fun convertImageUrlToBitmap(
        imageUri: Uri,
        context: Context
    ): Bitmap? {
        val loader = ImageLoader(context = context)
        val request = ImageRequest.Builder(context = context)
            .data(imageUri)
            .allowHardware(false)
            .build()
        val imageResult = loader.execute(request = request)
        return if (imageResult is SuccessResult) {
            (imageResult.drawable as BitmapDrawable).bitmap
        } else {
            null
        }
    }

    fun extractColorsFromBitmap(bitmap: Bitmap): Map<String, String> {
        return mapOf(
            "vibrant" to parseColorSwatch(
                color = Palette.from(bitmap).generate().vibrantSwatch
            ),
            "darkVibrant" to parseColorSwatch(
                color = Palette.from(bitmap).generate().darkVibrantSwatch
            ),
            "lightVibrant" to parseColorSwatch(
                color = Palette.from(bitmap).generate().lightVibrantSwatch
            ),
            "lightMuted" to parseColorSwatch(
                color = Palette.from(bitmap).generate().lightMutedSwatch
            ),
            "muted" to parseColorSwatch(
                color = Palette.from(bitmap).generate().mutedSwatch
            ),
            "darkMuted" to parseColorSwatch(
                color = Palette.from(bitmap).generate().darkVibrantSwatch
            ),
        )
    }

    private fun parseColorSwatch(color: Palette.Swatch?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color.rgb)
            return "#$parsedColor"
        } else {
            "#000000"
        }
    }

    private fun parseBodyColor(color: Int?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color)
            "#$parsedColor"
        } else {
            "#FFFFFF"
        }
    }

}