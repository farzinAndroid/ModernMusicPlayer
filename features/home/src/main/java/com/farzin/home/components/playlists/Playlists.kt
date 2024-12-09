package com.farzin.home.components.playlists

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.farzin.core_model.db.Playlist
import com.farzin.core_ui.common_components.DeleteDialog
import com.farzin.core_ui.common_components.EmptySectionText
import com.farzin.core_ui.common_components.MediaItem
import com.farzin.core_ui.common_components.TextBold
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.LyricDialogColor
import com.farzin.core_ui.theme.MainBlue
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.home.home.HomeViewmodel
import com.farzin.player.PlayerViewmodel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Playlists(
    homeViewmodel: HomeViewmodel = hiltViewModel(),
    playerViewmodel: PlayerViewmodel = hiltViewModel(),
    modifier: Modifier = Modifier,
    playlists: List<Playlist>,
    onPlaylistClicked: (playlist:Playlist) -> Unit,
) {


    var showCreatePlaylistDialog by remember { mutableStateOf(false) }
    var playlistNameValue by remember { mutableStateOf("") }
    var playlistToDelete by remember { mutableStateOf(Playlist()) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 64.dp),
        containerColor = MaterialTheme.colorScheme.BackgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showCreatePlaylistDialog = true
                },
                modifier = Modifier
                    .size(56.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.WhiteDarkBlue,
                content = {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.BackgroundColor,
                        modifier = Modifier.size(MaterialTheme.spacing.large32)
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
        content = {
            if (playlists.isNotEmpty()) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(bottom = 64.dp),
                ) {
                    itemsIndexed(playlists, key = { _, playlist -> playlist.id }) { index, playlist ->
                        Spacer(Modifier.height(MaterialTheme.spacing.small8))
                        MediaItem(
                            title = playlist.name,
                            subTitle = stringResource(com.farzin.core_ui.R.string.playlist),
                            modifier = Modifier
                                .clickable {
                                    onPlaylistClicked(playlist)
                                }
                                .animateItem(),
                            lightModePic = painterResource(com.farzin.core_ui.R.drawable.vinyl_blue),
                            darkModePic = painterResource(com.farzin.core_ui.R.drawable.vinyl_white),
                            shouldHaveMenu = true,
                            onDeleteClicked = {
                                playlistToDelete = playlist
                                playerViewmodel.showDeleteDialog = true
                            }
                        )

                    }
                }
            } else {
                EmptySectionText(stringResource(com.farzin.core_ui.R.string.no_playlists))
            }

            if (playerViewmodel.showDeleteDialog){
                DeleteDialog(
                    onConfirm = {
                        homeViewmodel.deletePlaylist(playlistToDelete)
                        playerViewmodel.showDeleteDialog = false
                    },
                    onDismiss = {
                        playerViewmodel.showDeleteDialog = false
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight(),
                )
            }

            if (showCreatePlaylistDialog) {
                Dialog(
                    onDismissRequest = { showCreatePlaylistDialog = false },
                ) {
                    CreatePlaylistDialogContent(
                        nameValue = playlistNameValue,
                        onValueChange = { playlistNameValue = it },
                        onConfirm = {
                            homeViewmodel.createPlaylist(
                                Playlist(
                                    name = playlistNameValue
                                )
                            )
                            showCreatePlaylistDialog = false
                        },
                        onCancel = {
                            showCreatePlaylistDialog = false
                        }
                    )
                }
            }
        }
    )

}


@Composable
fun CreatePlaylistDialogContent(
    nameValue: String,
    onValueChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {


    Column(
        modifier = Modifier
            .clip(Shapes().medium)
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.2f)
            .background(MaterialTheme.colorScheme.LyricDialogColor),
        horizontalAlignment = Alignment.Start
    ) {

        TextBold(
            text = stringResource(com.farzin.core_ui.R.string.save_playlist),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.WhiteDarkBlue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = MaterialTheme.spacing.medium16)
                .padding(vertical = MaterialTheme.spacing.medium16),
            textStyle = TextStyle(
                textAlign = TextAlign.Start
            )
        )

        OutlinedTextField(
            value = nameValue,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium16),
            shape = Shapes().medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.WhiteDarkBlue,
                unfocusedTextColor = MaterialTheme.colorScheme.WhiteDarkBlue,
                focusedPlaceholderColor = MaterialTheme.colorScheme.WhiteDarkBlue.copy(0.5f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.WhiteDarkBlue.copy(0.5f),
                focusedBorderColor = MaterialTheme.colorScheme.WhiteDarkBlue,
            ),
            singleLine = true,
            placeholder = {
                TextMedium(
                    text = stringResource(com.farzin.core_ui.R.string.playlist_name),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.WhiteDarkBlue.copy(0.5f),
                )
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = onCancel
            ) {
                TextMedium(
                    text = stringResource(com.farzin.core_ui.R.string.cancel),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.WhiteDarkBlue,
                )
            }

            TextButton(
                onClick = onConfirm
            ) {
                TextMedium(
                    text = stringResource(com.farzin.core_ui.R.string.save),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.WhiteDarkBlue,
                )
            }
        }


    }
}




















