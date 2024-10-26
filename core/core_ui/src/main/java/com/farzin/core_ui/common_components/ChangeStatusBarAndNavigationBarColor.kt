package com.farzin.core_ui.common_components

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.toArgb
import com.farzin.core_ui.theme.BackgroundColor

@Composable
fun ChangeStatusBarAndNavigationBarColor(isDarkMode: Boolean, context: ComponentActivity) {
    val color = MaterialTheme.colorScheme.BackgroundColor

    val statusBarStyle = if (isDarkMode) {
        SystemBarStyle.dark(
            color.toArgb(),
        )
    } else {
        SystemBarStyle.light(
            color.toArgb(),
            color.toArgb()
        )
    }

    DisposableEffect(isDarkMode) {
        context.enableEdgeToEdge(
            statusBarStyle = statusBarStyle,
            navigationBarStyle = if (!isDarkMode) {
                SystemBarStyle.light(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT
                )
            } else {
                SystemBarStyle.dark(
                    Color.TRANSPARENT
                )
            }
        )
        onDispose { }
    }
}