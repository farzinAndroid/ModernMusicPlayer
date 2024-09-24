package com.farzin.home.components

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.core_ui.utils.PaletteGenerator

@SuppressLint("MutableCollectionMutableState")
@Composable
fun FullPlayerImage(
    artWorkUri: Uri,
    context: Context,
) {

    var palette by remember { mutableStateOf<MutableMap<String, String>>(mutableMapOf()) }

    LaunchedEffect(artWorkUri) {
        val imageBitmap = PaletteGenerator.convertImageUrlToBitmap(artWorkUri, context)
        palette =
            imageBitmap?.let { PaletteGenerator.extractColorsFromBitmap(imageBitmap) as MutableMap<String, String> }!!
    }
    Box(
        modifier = Modifier
            .advancedShadow(
                color = palette["muted"]
                    ?.let { Color(android.graphics.Color.parseColor(it)) }
                    ?: MaterialTheme.colorScheme.WhiteDarkBlue,
                shadowBlurRadius = 50.dp,
                alpha = 0.4f,
                cornersRadius = 150.dp,
                offsetY = 30.dp,
                offsetX = 0.dp
            )
            .clip(RoundedCornerShape(MaterialTheme.spacing.large32))
            .size(300.dp)

    ) {
        SubcomposeAsyncImage(
            model = artWorkUri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
    }


}

fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 1f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}

fun Modifier.clipSides(
    left: Boolean = true,
    top: Boolean = true,
    right: Boolean = true,
    bottom: Boolean = true,
): Modifier = drawWithContent {
    clipRect(
        left = if (left) 0f else -Float.MAX_VALUE,
        top = if (top) 0f else -Float.MAX_VALUE,
        right = if (right) size.width else Float.MAX_VALUE,
        bottom = if (bottom) size.height else Float.MAX_VALUE,
    ) {
        this@drawWithContent.drawContent()
    }
}