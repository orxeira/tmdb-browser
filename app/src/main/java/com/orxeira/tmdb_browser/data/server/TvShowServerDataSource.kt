package com.orxeira.tmdb_browser.data.server

import com.orxeira.tmdb_browser.domain.TvShow
import retrofit2.Response

class TvShowServerDataSource(
    private val apiKey: String
): TvShowRemoteDataSource {
    override suspend fun getTopRatedTvShows(page: Int) =
        RemoteConnection.service
            .listTopRatedTvShows(apiKey, page)



}

