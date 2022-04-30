package com.orxeira.tmdb_browser.data.server

class TvShowServerDataSource(
    private val apiKey: String
) : TvShowRemoteDataSource {
    override suspend fun getTopRatedTvShows(page: Int) =
        RemoteConnection.service
            .listTopRatedTvShows(apiKey, page)

    override suspend fun getSimilarShows(id: Int) =
        RemoteConnection.service
            .getSimilarShows(id,apiKey)

}