package com.farzin.player.player

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farzin.core_model.MusicState
import com.farzin.core_model.PlaybackMode
import com.farzin.core_model.Song
import com.farzin.core_ui.common_components.deleteLauncher
import com.farzin.core_ui.theme.BackgroundColor
import com.farzin.core_ui.theme.spacing
import com.farzin.player.PlayerViewmodel
import com.farzin.player.components.LyricsDialogContent
import kotlinx.coroutines.flow.asFlow
import kotlin.math.absoluteValue

@Composable
fun FullPlayer(
    songs: List<Song>,
    onSkipToIndex: (Int) -> Unit,
    onBackClicked: () -> Unit,
    onPlaybackModeClicked: () -> Unit,
    onToggleLikeButton: (id: String, isFavorite: Boolean) -> Unit,
    currentPosition: Long,
    musicState: MusicState,
    onSeekTo: (Float) -> Unit,
    onPrevClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit,
    playbackMode: PlaybackMode,
    playerViewmodel: PlayerViewmodel = hiltViewModel(),
) {

    val context = LocalContext.current
    val currentSong = songs[musicState.currentSongIndex]

    var showLyricsDialog by remember { mutableStateOf(false) }
    val lyrics by playerViewmodel.lyrics.collectAsStateWithLifecycle()

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
            .background(MaterialTheme.colorScheme.BackgroundColor)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {


        FullPlayerTopBar(
            onBackClicked = onBackClicked,
            onLyricsClicked = {
                showLyricsDialog = true
                playerViewmodel.getLyrics(currentSong)
            },
            song = currentSong,
            modifier = Modifier
                .weight(0.05f)
        )

        if (showLyricsDialog){
            Dialog(
                onDismissRequest = {showLyricsDialog = false},
            ) {
                LyricsDialogContent(
                    lyrics=lyrics
                )
            }
        }


        Box(
            modifier = Modifier
                .weight(0.55f)
                .wrapContentSize()
        ) {
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


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
        ) {

            Spacer(Modifier.height(MaterialTheme.spacing.large32))

            FullPlayerRepeatShuffleLike(
                onToggleLikeButton = { onToggleLikeButton(currentSong.mediaId, it) },
                onPlaybackModeClicked = onPlaybackModeClicked,
                playbackMode = playbackMode,
                isFavorite = currentSong.isFavorite
            )

            Spacer(Modifier.height(60.dp))


            FullPlayerTimeSlider(
                currentPosition = currentPosition,
                musicState = musicState,
                onSeekTo = onSeekTo
            )

            Spacer(Modifier.height(40.dp))

            FullPlayerMusicController(
                onPrevClicked = onPrevClicked,
                onNextClicked = onNextClicked,
                onPlayPauseClicked = onPlayPauseClicked,
                musicState = musicState
            )
        }




    }

    BackHandler {
        onBackClicked()
    }

}

private fun PagerState.calculatePageOffset(page: Int) =
    ((currentPage - page) + currentPageOffsetFraction).absoluteValue