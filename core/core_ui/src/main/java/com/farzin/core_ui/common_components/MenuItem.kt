package com.farzin.core_ui.common_components

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val text:String = "",
    val onClick:()->Unit = {},
    val iconVector:ImageVector? = null
)
