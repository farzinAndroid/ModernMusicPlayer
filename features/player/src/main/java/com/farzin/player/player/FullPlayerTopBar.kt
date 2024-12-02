package com.farzin.player.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.player.R

@Composable
fun FullPlayerTopBar(
    onBackClicked: () -> Unit,
    onLyricsClicked: () -> Unit,
    song: Song,
    modifier: Modifier = Modifier
) {


    var isExpanded by remember { mutableStateOf(false) }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.semiLarge24),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(
            onClick = { onBackClicked() },
            modifier = Modifier
                .size(26.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                tint = MaterialTheme.colorScheme.WhiteDarkBlue
            )
        }

        TextMedium(
            text = stringResource(com.farzin.core_ui.R.string.playing),
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            maxLine = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.medium16)
                .weight(1f),
            textStyle = TextStyle(
                textAlign = TextAlign.Center
            )
        )

        IconButton(
            onClick = { isExpanded = !isExpanded },
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

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier,
            offset = DpOffset(LocalConfiguration.current.screenWidthDp.dp, 0.dp),
            containerColor = Color.White,
        ) {
            DropdownMenuItem(
                text = {
                    TextMedium(
                        text = stringResource(com.farzin.core_ui.R.string.lyrics),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                },
                onClick = {
                    onLyricsClicked()
                    isExpanded = false
                }
            )

        }

    }


}