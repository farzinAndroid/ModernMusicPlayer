package com.farzin.player.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.common_components.TextRegular
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun FullPlayerTitleArtist(
    title:String,
    artist:String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextMedium(
            text = title,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            modifier = Modifier
                .fillMaxWidth(),
            maxLine = 2,
            overflow = TextOverflow.Ellipsis,
            textStyle = TextStyle(
                textAlign = TextAlign.Center
            )
        )

        Spacer(Modifier.height(MaterialTheme.spacing.extraSmall4))

        TextRegular(
            text = artist,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
        )
    }

}