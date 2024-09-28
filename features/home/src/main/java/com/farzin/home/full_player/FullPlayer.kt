package com.farzin.home.full_player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.farzin.core_model.MusicState
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.Song
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.spacing
import com.farzin.home.components.FullPlayerImage
import com.farzin.home.components.FullPlayerRepeatShuffleLike
import com.farzin.home.components.FullPlayerTimeSlider
import com.farzin.home.components.FullPlayerTitleArtist
import com.farzin.home.components.FullPlayerTopBar
import kotlin.math.absoluteValue

@Composable
fun FullPlayer(
    songs: List<Song>,
    onSkipToIndex: (Int) -> Unit,
    onBackClicked:()->Unit,
    onRepeatClicked:()->Unit,
    onToggleLikeButton:()->Unit,
    currentPosition:Long,
    musicState: MusicState,
    onSeekTo:(Float)->Unit,
    playbackMode: PlaybackMode
) {

    val context = LocalContext.current

    val currentSong = songs[musicState.currentSongIndex]

    val pagerState = rememberPagerState(
        pageCount = { songs.size.coerceAtLeast(1) },
        initialPage = musicState.currentSongIndex
    )

    LaunchedEffect(currentSong, musicState.currentMediaId, musicState.currentSongIndex) {
        if (currentSong.mediaId != musicState.currentMediaId) return@LaunchedEffect

        if (musicState.currentSongIndex != pagerState.currentPage) {
            pagerState.animateScrollToPage(page = musicState.currentSongIndex)
        }
    }

    LaunchedEffect(
        currentSong,
        musicState.currentMediaId,
        pagerState.currentPage,
        pagerState.isScrollInProgress
    ) {

        if (currentSong.mediaId != musicState.currentMediaId) return@LaunchedEffect

        if (musicState.currentSongIndex != pagerState.currentPage && !pagerState.isScrollInProgress) {
            onSkipToIndex(pagerState.currentPage)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.BackgroundColor),
        verticalArrangement = Arrangement.Top,
    ) {


        FullPlayerTopBar(
            onBackClicked = onBackClicked,
            albumName = currentSong.album
        )


        Box(
            modifier = Modifier
                .wrapContentSize()
        ){
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

            FullPlayerTitleArtist(
                title = currentSong.title,
                artist = currentSong.artist,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )


        }

        Spacer(Modifier.height(MaterialTheme.spacing.large32))

        FullPlayerRepeatShuffleLike(
            onToggleLikeButton = onToggleLikeButton,
            onRepeatClicked = onRepeatClicked,
            playbackMode=playbackMode
        )

        Spacer(Modifier.height(60.dp))


        FullPlayerTimeSlider(
            currentPosition = currentPosition,
            musicState = musicState,
            onSeekTo = onSeekTo
        )


    }

}

private fun PagerState.calculatePageOffset(page: Int) =
    ((currentPage - page) + currentPageOffsetFraction).absoluteValue