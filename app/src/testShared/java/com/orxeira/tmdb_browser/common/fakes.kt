package com.orxeira.tmdb_browser.common

import com.orxeira.tmdb_browser.data.database.TvShowLocalDatasource
import com.orxeira.tmdb_browser.data.server.TvShowRemoteDataSource
import com.orxeira.tmdb_browser.domain.TvShow

private val postFactory = TvShowFactory()
val fakeTvShows = listOf(
    postFactory.createTvShow(),
    postFactory.createTvShow(),
    postFactory.createTvShow(),
    postFactory.createTvShow(),
    postFactory.createTvShow()
)

class FakeRemoteDataSource : TvShowRemoteDataSource {
    override suspend fun getTopRatedTvShows(page: Int): List<TvShow> {
        return fakeTvShows
    }

    override suspend fun getSimilarShows(id: Int): List<TvShow> {
        return fakeTvShows
    }
}

class FakeLocalDataSource : TvShowLocalDatasource {

    val inMemoryTvShows = mutableListOf<TvShow>()

    override suspend fun getTvShows(size: Int): List<TvShow> {
        return inMemoryTvShows
    }

    override suspend fun addTvShows(tvShows: List<TvShow>) {
        inMemoryTvShows.addAll(tvShows)
    }

}