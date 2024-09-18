package com.farzin.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.farzin.core_ui.theme.BackgroundColor

@Composable
fun HomeTab(
    selected:Boolean,
    onClick:()->Unit,
    modifier:Modifier = Modifier,
    unselectedContentColor:Color,
    selectedContentColor:Color,
    content:@Composable ColumnScope.() -> Unit,
    enabled:Boolean = true
) {



    Tab(
        selected = selected,
        onClick = onClick,
        content = content,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        modifier = modifier.background(MaterialTheme.colorScheme.BackgroundColor),
        enabled = true,
    )

}