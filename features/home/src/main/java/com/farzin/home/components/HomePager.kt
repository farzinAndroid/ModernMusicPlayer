package com.farzin.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_model.Album
import com.farzin.core_model.Artist
import com.farzin.core_model.Folder
import com.farzin.core_model.MusicState
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.home.components.albums.Albums
import com.farzin.home.components.artists.Artists
import com.farzin.home.components.favorites.Favorites
import com.farzin.home.components.folders.Folders
import com.farzin.home.components.recently_added.RecentlyAdded
import com.farzin.home.components.songs.Songs
import com.farzin.home.home.MediaTab
import kotlinx.coroutines.launch

@Composable
fun HomePager(
    onSongClick: (index:Int,songs:List<Song>) -> Unit,
    onAlbumClick: (Long) -> Unit,
    onArtistClick: (Long) -> Unit,
    onFolderClick: (String) -> Unit,
    onFavoriteClick: (id:String,isFavorite:Boolean) -> Unit,
    currentPlayingSongId: String,
    songs: List<Song>,
    favoriteSongs: List<Song>,
    musicState: MusicState,
    albums: List<Album>,
    artists: List<Artist>,
    folders:List<Folder>,
    recentSongs: List<Song>,
) {

    val scope = rememberCoroutineScope()
    val tabs = remember { MediaTab.entries.toTypedArray() }
    val pagerState = rememberPagerState(
        pageCount = { tabs.size },
    )
    val selectedTabIndex = pagerState.currentPage


    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier
            .padding(top = MaterialTheme.spacing.small8)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.BackgroundColor),
        tabs = {
            tabs.forEachIndexed { index, mediaTab ->
                val selected = index == pagerState.currentPage

                HomeTab(
                    unselectedContentColor = MaterialTheme.colorScheme.Gray,
                    selectedContentColor = MaterialTheme.colorScheme.WhiteDarkBlue,
                    selected = selected,
                    content = {
                        TextMedium(
                            text = stringResource(mediaTab.titleResource),
                            fontSize = 16.sp,
                            color = if (selected) MaterialTheme.colorScheme.WhiteDarkBlue else MaterialTheme.colorScheme.Gray,
                            modifier = Modifier
                                .padding(horizontal = MaterialTheme.spacing.small8)
                                .padding(vertical = 14.dp)
                        )
                    },
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(index) }
                    },
                )
            }
        },
        divider = {},
        containerColor = MaterialTheme.colorScheme.BackgroundColor,
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = MaterialTheme.colorScheme.WhiteDarkBlue,
                    height = 1.dp
                )
            }
        }
    )


    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
    ) { page ->
        when (page) {
            MediaTab.Songs.ordinal -> {
                Songs(
                    onClick = onSongClick,
                    currentPlayingSongId = currentPlayingSongId,
                    songs = songs,
                    onToggleFavorite = onFavoriteClick,
                )
            }

            MediaTab.Albums.ordinal -> {
                Albums(
                    albums = albums,
                    onClick = { albumId ->
                        onAlbumClick(albumId)
                    }
                )
            }

            MediaTab.Artists.ordinal -> {
                Artists(
                    artists =artists,
                    onArtistClick = onArtistClick
                )
            }


            MediaTab.Folders.ordinal -> {
                Folders(
                    folders = folders,
                    onFolderClick = onFolderClick
                )
            }

            MediaTab.Favorites.ordinal->{
                Favorites(
                    favoriteSongs = favoriteSongs,
                    onClick = onSongClick,
                    onToggleFavorite = onFavoriteClick,
                    currentPlayingSongId = currentPlayingSongId
                )
            }

            MediaTab.RecentlyAdded.ordinal->{
                RecentlyAdded(
                    onClick = onSongClick,
                    currentPlayingSongId = currentPlayingSongId,
                    recentSongs = recentSongs,
                    onToggleFavorite = onFavoriteClick,
                )
            }
        }
    }
}