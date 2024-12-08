package com.farzin.core_ui.common_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.MainBlue
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun MediaItem(
    modifier: Modifier = Modifier,
    title:String,
    subTitle:String,
    darkModePic:Painter,
    lightModePic:Painter,
    shouldHaveMenu:Boolean = false
) {


    var isMenuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = MaterialTheme.spacing.medium16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = if (isSystemInDarkTheme()) darkModePic else lightModePic,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(57.dp),
            )
            Spacer(Modifier.width(MaterialTheme.spacing.medium16))

            Column {
                TextBold(
                    text = title,
                    color = MaterialTheme.colorScheme.WhiteDarkBlue,
                    fontSize = 16.sp,
                    maxLine = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (subTitle.isNotEmpty()){
                    TextRegular(
                        text = subTitle,
                        color = MaterialTheme.colorScheme.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }



        Column {
            if (shouldHaveMenu){
                IconButton(
                    onClick = { isMenuExpanded = !isMenuExpanded },
                    modifier = Modifier
                        .size(26.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize(),
                        tint = MaterialTheme.colorScheme.WhiteDarkBlue
                    )
                }


            }

            DropdownMenu(
                expanded = isMenuExpanded,
                onDismissRequest = { isMenuExpanded = false },
                modifier = Modifier,
                offset = DpOffset(LocalConfiguration.current.screenWidthDp.dp, 0.dp),
                containerColor = Color.White,
                border = BorderStroke(1.dp,MaterialTheme.colorScheme.MainBlue)
            ) {
                DropdownMenuItem(
                    text = {
                        TextMedium(
                            text = stringResource(com.farzin.core_ui.R.string.delete_playlist),
                            color = MaterialTheme.colorScheme.MainBlue,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        isMenuExpanded = false
                    },
                    modifier = Modifier
                        .height(40.dp)
                )

            }
        }

    }
}