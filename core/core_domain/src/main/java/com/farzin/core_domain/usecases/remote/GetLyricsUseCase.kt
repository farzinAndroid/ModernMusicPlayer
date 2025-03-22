package com.farzin.core_domain.usecases.remote

import com.farzin.core_domain.repository.remote.LyricsRepository
import com.farzin.core_model.remote.Lyric
import com.farzin.core_model.remote.NetworkResult
import javax.inject.Inject

class GetLyricsUseCase @Inject constructor(private val lyricsApiRepository: LyricsRepository) {
    suspend operator fun invoke(songTitle: String, artist: String) : NetworkResult<Lyric> = lyricsApiRepository.getLyrics(artist, songTitle)
}