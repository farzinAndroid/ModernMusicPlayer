package com.farzin.core_ui.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_model.Artist
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun ArtistItem(
    artist:Artist,
    onClick:(Long)->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                onClick(artist.id)
            }
            .padding(horizontal = MaterialTheme.spacing.medium16)
    ) {
        Image(
            painter = painterResource(com.farzin.core_ui.R.drawable.artist_background),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(57.dp),
        )

        Spacer(Modifier.width(MaterialTheme.spacing.medium16))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

            TextBold(
                text = artist.name,
                color = MaterialTheme.colorScheme.WhiteDarkBlue,
                fontSize = 16.sp,
                maxLine = 1,
                overflow = TextOverflow.Ellipsis
            )

            TextRegular(
                text = stringResource(com.farzin.core_ui.R.string.artist),
                color = MaterialTheme.colorScheme.Gray,
                fontSize = 12.sp
            )

        }

    }


}

