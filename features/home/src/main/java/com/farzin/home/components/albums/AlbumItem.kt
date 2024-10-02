package com.farzin.home.components.albums

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.farzin.core_model.Album
import com.farzin.core_ui.common_components.ErrorImage
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.common_components.TextRegular
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.core_ui.utils.PaletteGenerator
import com.farzin.home.full_player.advancedShadow

@SuppressLint("MutableCollectionMutableState")
@Composable
fun AlbumItem(
    modifier: Modifier = Modifier,
    album: Album,
    onAlbumClicked:()->Unit
) {

    val context = LocalContext.current

    var palette by remember { mutableStateOf<MutableMap<String, String>>(mutableMapOf()) }

    LaunchedEffect(album.artworkUri) {
        try {
            val imageBitmap = PaletteGenerator.convertImageUrlToBitmap(album.artworkUri, context)
            palette =
                imageBitmap?.let { PaletteGenerator.extractColorsFromBitmap(imageBitmap) as MutableMap<String, String> }!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = modifier
            .padding(top = MaterialTheme.spacing.semiLarge24)
            .padding(horizontal = MaterialTheme.spacing.semiLarge24)
            .clickable { onAlbumClicked() }
            .height(200.dp)
            .width(150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .advancedShadow(
                    color = palette["muted"]
                        ?.let { Color(android.graphics.Color.parseColor(it)) }
                        ?: Color.Transparent,
                    shadowBlurRadius = 50.dp,
                    alpha = 0.8f,
                    cornersRadius = 150.dp,
                    offsetY = 30.dp,
                    offsetX = 0.dp
                )
                .clip(RoundedCornerShape(MaterialTheme.spacing.large32))
                .fillMaxWidth()
                .height(145.dp)

        ) {
            SubcomposeAsyncImage(
                model = album.artworkUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize(),
                error = {
                    ErrorImage()
                }
            )
        }

        Spacer(Modifier.height(MaterialTheme.spacing.semiMedium12))

        TextMedium(
            text = album.name,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            maxLine = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(textAlign = TextAlign.Center)
        )

        Spacer(Modifier.height(MaterialTheme.spacing.semiSmall6))

        TextRegular(
            text = album.artist,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.Gray,
            maxLine = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(textAlign = TextAlign.Center)
        )
    }

}