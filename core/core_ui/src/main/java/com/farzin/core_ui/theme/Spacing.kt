package com.farzin.core_ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val extraSmall4: Dp = 4.dp,
    val semiSmall6: Dp = 6.dp,
    val small8: Dp = 8.dp,
    val biggerSmall10: Dp = 10.dp,
    val semiMedium12: Dp = 12.dp,
    val medium16: Dp = 16.dp,
    val biggerMedium20: Dp = 20.dp,
    val semiLarge24: Dp = 24.dp,
    val large32: Dp = 32.dp,
    val extraLarge64: Dp = 64.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current