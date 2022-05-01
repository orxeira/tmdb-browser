package com.orxeira.tmdb_browser.data.server

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {

    @GET("tv/top_rated")
    suspend fun listTopRatedTvShows(
        @Query("api_key") apiKey: String
    ): RemoteResult

    @GET("tv/top_rated")
    suspend fun listTopRatedTvShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int?
    ): RemoteResult

    @GET("tv/{id}/similar")
    suspend fun getSimilarShows(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): RemoteResult

}