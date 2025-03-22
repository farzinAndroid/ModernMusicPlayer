package com.farzin.core_domain.repository.remote

import com.farzin.core_model.remote.Lyric
import com.farzin.core_model.remote.NetworkResult
import javax.inject.Inject


class LyricsRepository @Inject constructor(private val api: LyricsApiInterface) : BaseApiResponse() {

    suspend fun getLyrics(songTitle:String,artist:String) : NetworkResult<Lyric> = safeApiCall {
        api.getLyrics(artist, songTitle)
    }

}