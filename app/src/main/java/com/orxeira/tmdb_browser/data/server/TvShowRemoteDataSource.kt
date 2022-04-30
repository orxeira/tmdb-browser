package com.orxeira.tmdb_browser.data.server

import retrofit2.Response

interface TvShowRemoteDataSource {
    suspend fun getTopRatedTvShows(page:Int): Response<RemoteResult>

    suspend fun getSimilarShows(id: Int): Response<RemoteResult>
}