package com.farzin.core_ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val ColorScheme.WhiteDarkBlue: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xff091227) else Color(0xffF7FAFF)


val ColorScheme.DarkText: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xffF7FAFF) else Color(0xff091227)


val ColorScheme.Gray: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xff8996B8) else Color(0x998996B8)

val ColorScheme.DarkGray: Color
    @Composable
    get() = Color(0xff555B6A)