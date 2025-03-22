package com.farzin.core_data.remote

import com.farzin.core_common.Constants
import com.farzin.core_domain.repository.remote.LyricsApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun interceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient = OkHttpClient.Builder()
        .writeTimeout(Constants.TIMEOUT,TimeUnit.SECONDS)
        .readTimeout(Constants.TIMEOUT,TimeUnit.SECONDS)
        .connectTimeout(Constants.TIMEOUT,TimeUnit.SECONDS)
        .addInterceptor {chain->
            val request = chain.request().newBuilder()

            request
                .addHeader("x-happi-token",Constants.HAPPI_X_API_KEY)

            chain.proceed(request.build())

        }
        .addInterceptor(interceptor())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.LYRICS_BASE_URL)
            .build()

    @Provides
    @Singleton
    fun providesLyricsApiInterface(retrofit: Retrofit) : LyricsApiInterface = retrofit.create(
        LyricsApiInterface::class.java)

}