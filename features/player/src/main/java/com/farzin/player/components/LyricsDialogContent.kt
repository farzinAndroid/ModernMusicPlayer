package com.farzin.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.common_components.TextRegular
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun LyricsDialogContent(
    lyrics: String,
) {

    val text = when (lyrics) {
        "" -> stringResource(com.farzin.core_ui.R.string.please_wait)
        else -> lyrics
    }

    Column(
        modifier = Modifier
            .clip(Shapes().medium)
            .fillMaxWidth(1f)
            .fillMaxHeight(0.9f)
            .background(MaterialTheme.colorScheme.BackgroundColor),
    ) {

        TextRegular(
            text = stringResource(com.farzin.core_ui.R.string.lyrics_description),
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            fontSize = 10.sp,
            textStyle = TextStyle(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(MaterialTheme.spacing.small8)
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextMedium(
                text = text,
                color = MaterialTheme.colorScheme.WhiteDarkBlue,
                fontSize = 12.sp,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small8)
            )
        }
    }

}