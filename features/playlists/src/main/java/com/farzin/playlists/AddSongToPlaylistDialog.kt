package com.farzin.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.farzin.core_model.MusicState
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.TextBold
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.LyricDialogColor
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun AddSongToPlaylistDialog(
    modifier: Modifier = Modifier,
    songs: List<Song>,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = modifier
                .clip(Shapes().medium)
                .background(MaterialTheme.colorScheme.LyricDialogColor)
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(MaterialTheme.spacing.medium16))


            TextBold(
                text = stringResource(com.farzin.core_ui.R.string.add_song_to_playlist_text),
                color = MaterialTheme.colorScheme.WhiteDarkBlue,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center
                )
            )

            Spacer(Modifier.height(MaterialTheme.spacing.medium16))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = MaterialTheme.spacing.medium16),
                leadingIcon = {
                    Icon(
                        painter = painterResource(com.farzin.core_ui.R.drawable.search),
                        contentDescription = "",
                        modifier = Modifier
                            .size(MaterialTheme.spacing.medium16)
                    )
                }
            )

            Spacer(
                Modifier
                    .height(MaterialTheme.spacing.small8)
                    .background(MaterialTheme.colorScheme.Gray)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(songs) { index, song ->
                    Spacer(Modifier.height(MaterialTheme.spacing.small8))
                    AddSongToPlaylistItem(
                        song = song,
                        onClick = {}
                    )
                }
            }
        }

    }

}