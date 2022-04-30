package com.orxeira.tmdb_browser.data.server

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("tv/top_rated")
    suspend fun listTopRatedTvShows(
        @Query("api_key") apiKey: String
    ): Response<RemoteResult>

    @GET("tv/top_rated")
    suspend fun listTopRatedTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int?
    ): Response<RemoteResult>

}