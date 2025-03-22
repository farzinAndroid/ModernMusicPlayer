package com.farzin.core_domain.repository.remote

import com.farzin.core_model.remote.Lyric
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LyricsApiInterface {

    @GET("lyrics")
    suspend fun getLyrics(
        @Query("artist") artist: String,
        @Query("track") songTitle: String
    ): Response<Lyric>

}