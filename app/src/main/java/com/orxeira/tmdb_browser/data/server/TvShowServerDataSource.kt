package com.orxeira.tmdb_browser.data.server

/**
 * Specific implementation for the remote datasource that uses Retrofit as a provider.
 */
class TvShowServerDataSource(
    private val apiKey: String
) : TvShowRemoteDataSource {
    override suspend fun getTopRatedTvShows(page: Int) =
        RemoteConnection.service
            .listTopRatedTvShows(apiKey, page)
            .results
            .toDomainModel()


    override suspend fun getSimilarShows(id: Int) =
        RemoteConnection.service
            .getSimilarShows(id,apiKey)
            .results
            .toDomainModel()
}