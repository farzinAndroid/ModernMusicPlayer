package com.farzin.home.full_player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.SubcomposeAsyncImage
import com.farzin.core_model.Song
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.WhiteDarkBlue
import com.farzin.core_ui.theme.spacing
import com.farzin.home.components.FullPlayerImage
import kotlin.math.absoluteValue

@Composable
fun FullPlayer(
    songs: List<Song>,
    currentSongIndex: Int,
    currentMediaId: String,
    onSkipToIndex: (Int) -> Unit,
) {

    val context = LocalContext.current

    val currentSong = songs[currentSongIndex]

    val pagerState = rememberPagerState(
        pageCount = { songs.size.coerceAtLeast(1) },
        initialPage = currentSongIndex
    )

    LaunchedEffect(currentSong, currentMediaId, currentSongIndex) {
        if (currentSong.mediaId != currentMediaId) return@LaunchedEffect

        if (currentSongIndex != pagerState.currentPage) {
            pagerState.animateScrollToPage(page = currentSongIndex)
        }
    }

    LaunchedEffect(
        currentSong,
        currentMediaId,
        pagerState.currentPage,
        pagerState.isScrollInProgress
    ) {

        if (currentSong.mediaId != currentMediaId) return@LaunchedEffect

        if (currentSongIndex != pagerState.currentPage && !pagerState.isScrollInProgress) {
            onSkipToIndex(pagerState.currentPage)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.BackgroundColor),
        verticalArrangement = Arrangement.Top,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) { }


        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(0)
            ),
            contentPadding = PaddingValues(horizontal = 56.dp),
            pageSpacing = MaterialTheme.spacing.semiLarge24,
            verticalAlignment = Alignment.Top

        ) { index ->


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = pagerState.calculatePageOffset(pagerState.currentPage)
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
            ) {
                FullPlayerImage(
                    artWorkUri = songs[index].artworkUri,
                    context = context
                )
            }

        }


    }

}

@OptIn(ExperimentalFoundationApi::class)
private fun PagerState.calculatePageOffset(page: Int) =
    ((currentPage - page) + currentPageOffsetFraction).absoluteValue