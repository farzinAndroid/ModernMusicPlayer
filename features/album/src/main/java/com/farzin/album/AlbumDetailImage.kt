package com.farzin.album

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.farzin.core_ui.common_components.ErrorImage
import com.farzin.core_ui.theme.spacing

@Composable
fun AlbumDetailImage(
    artworkUri:String
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(MaterialTheme.spacing.medium16))
            .size(240.dp),
        contentAlignment = Alignment.Center
    ){

        SubcomposeAsyncImage(
            model = artworkUri,
            contentDescription = "",
            error = {
                ErrorImage()
            },
            modifier = Modifier
                .fillMaxSize()
        )

    }

}