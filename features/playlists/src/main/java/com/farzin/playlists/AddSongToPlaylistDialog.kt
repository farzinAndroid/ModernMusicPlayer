package com.farzin.playlists

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farzin.core_model.Song
import com.farzin.core_model.db.PlaylistSong
import com.farzin.core_model.db.toSongDB
import com.farzin.core_ui.common_components.TextBold
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.LyricDialogColor
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing

@Composable
fun AddSongToPlaylistDialog(
    modifier: Modifier = Modifier,
    playlistViewmodel: PlaylistViewmodel,
    songs: List<Song>,
    onDismiss: () -> Unit,
    onConfirm: (List<PlaylistSong>) -> Unit,
    songsInPlaylist: List<PlaylistSong>,
    playlistId: Int,
) {

    val query by playlistViewmodel.query.collectAsState()
    val searchDetails by playlistViewmodel.searchDetails.collectAsStateWithLifecycle()

    var selectedSongs by remember { mutableStateOf<Set<Song>>(emptySet()) }

    LaunchedEffect(selectedSongs) {
        Log.e("TAG", selectedSongs.toString())
    }

    Dialog(
        onDismissRequest = {
            playlistViewmodel.clear()
            selectedSongs.toMutableSet().clear()
            onDismiss()
        }
    ) {

        Column(
            modifier = modifier
                .clip(Shapes().medium)
                .background(MaterialTheme.colorScheme.LyricDialogColor)
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box() {

                Column {
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
                        value = query,
                        onValueChange = {
                            playlistViewmodel.changeQuery(it)
                        },
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
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.WhiteDarkBlue,
                            unfocusedTextColor = MaterialTheme.colorScheme.WhiteDarkBlue,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.WhiteDarkBlue.copy(
                                0.5f
                            ),
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.WhiteDarkBlue.copy(
                                0.5f
                            ),
                            focusedBorderColor = MaterialTheme.colorScheme.WhiteDarkBlue,
                        ),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 14.sp
                        )
                    )

                    Spacer(
                        Modifier
                            .height(MaterialTheme.spacing.small8)
                            .background(MaterialTheme.colorScheme.Gray)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = MaterialTheme.spacing.extraLarge64)
                    ) {

                        if (query.isEmpty() || query.isBlank()) {
                            itemsIndexed(songs) { index, song ->
                                Spacer(Modifier.height(MaterialTheme.spacing.small8))
                                AddSongToPlaylistItem(
                                    song = song,
                                    onClick = {
                                        selectedSongs = if (it in selectedSongs) {
                                            selectedSongs - it
                                        } else {
                                            selectedSongs + it
                                        }
                                    },
                                    modifier = Modifier
                                        .animateItem(),
                                    isSelected = song in selectedSongs
                                )
                            }
                        } else {
                            itemsIndexed(searchDetails.songs) { index, song ->
                                Spacer(Modifier.height(MaterialTheme.spacing.small8))
                                AddSongToPlaylistItem(
                                    song = song,
                                    onClick = {
                                        selectedSongs = if (it in selectedSongs) {
                                            selectedSongs - it
                                        } else {
                                            selectedSongs + it
                                        }
                                    },
                                    modifier = Modifier
                                        .animateItem(),
                                    isSelected = song in selectedSongs
                                )
                            }
                        }
                    }
                }

                DialogConfirmDismissSection(
                    onConfirm = {
                        playlistViewmodel.clear()
                        val playlistSongs = mutableListOf<PlaylistSong>()
                        selectedSongs.forEach { song ->
                            playlistSongs.add(
                                PlaylistSong(
                                    song = song.toSongDB(),
                                    playlistId = playlistId
                                )
                            )
                        }

                        onConfirm(playlistSongs)

                    },
                    onDismiss = {
                        playlistViewmodel.clear()
                        onDismiss()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    isSongSelected = selectedSongs.isNotEmpty()
                )

            }


        }

    }

}