package com.farzin.core_ui.common_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.R
import com.farzin.core_ui.theme.WhiteDarkBlue

@Composable
fun Loading() {


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        TextMedium(
            text = stringResource(R.string.loading),
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            fontSize = 20.sp
        )
    }
}