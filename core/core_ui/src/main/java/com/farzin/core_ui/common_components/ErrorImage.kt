package com.farzin.core_ui.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.farzin.core_ui.R

@Composable
fun ErrorImage() {

    Image(
        painter = painterResource(R.drawable.music_logo),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )

}