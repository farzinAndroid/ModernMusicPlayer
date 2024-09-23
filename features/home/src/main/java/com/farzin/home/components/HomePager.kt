package com.farzin.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farzin.core_model.MusicState
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.TextMedium
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.Gray
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.home.home.MediaTab
import kotlinx.coroutines.launch

@Composable
fun HomePager(
    onSongClick: (Int) -> Unit,
    currentPlayingSongId: String,
    songs: List<Song>,
    musicState: MusicState
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
                            text = mediaTab.name,
                            fontSize = 16.sp,
                            color = if (selected) MaterialTheme.colorScheme.WhiteDarkBlue else MaterialTheme.colorScheme.Gray,
                            modifier = Modifier
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
        indicator = {tabPositions ->
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
                    onToggleFavorite = {},
                    songs = songs,
                    musicState = musicState
                )
            }

            MediaTab.Artists.ordinal -> {

            }

            MediaTab.Albums.ordinal -> {

            }

            MediaTab.Folders.ordinal -> {

            }

        }
    }
}