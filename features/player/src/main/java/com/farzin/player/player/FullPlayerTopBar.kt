package com.farzin.player.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

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
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.medium16)
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