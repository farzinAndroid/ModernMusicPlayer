package com.farzin.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.home.R

@Composable
fun FullPlayerTopBar(
    onBackClicked:()->Unit,
    albumName:String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = MaterialTheme.spacing.semiLarge24),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(
            onClick = { onBackClicked() },
            modifier = Modifier
                .size(26.dp)
        ){
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                tint = MaterialTheme.colorScheme.WhiteDarkBlue
            )
        }

        TextMedium(
            text = albumName,
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            maxLine = 1,
            fontSize = 20.sp
        )

        Icon(
            painter = painterResource(com.farzin.core_ui.R.drawable.ic_pause),
            contentDescription = "",
            modifier = Modifier
                .size(MaterialTheme.spacing.semiLarge24),
            tint = MaterialTheme.colorScheme.BackgroundColor
        )

    }

}