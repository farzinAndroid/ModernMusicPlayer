package com.farzin.home.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.farzin.core_ui.theme.WhiteDarkBlue

@Composable
fun ControllerButton(
    painter: Painter,
    modifier: Modifier = Modifier
) {

    Icon(
        painter = painter,
        contentDescription = "",
        modifier = modifier,
        tint = MaterialTheme.colorScheme.WhiteDarkBlue
    )

}