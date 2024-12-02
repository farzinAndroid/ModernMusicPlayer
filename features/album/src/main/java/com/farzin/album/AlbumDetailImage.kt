package com.farzin.album

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.farzin.core_ui.common_components.ErrorImage
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun AlbumDetailImage(
    artworkUri:String,
    albumName:String
) {


    Column(
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SubcomposeAsyncImage(
            model = artworkUri,
            contentDescription = "",
            error = {
                ErrorImage()
            },
            modifier = Modifier
                .clip(RoundedCornerShape(MaterialTheme.spacing.medium16))
                .size(240.dp),
        )

        Spacer(Modifier.height(MaterialTheme.spacing.semiLarge24))

        TextMedium(
            text = albumName,
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            maxLine = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.medium16)
        )

    }


}