package com.orxeira.tmdb_browser.data.database

import com.orxeira.tmdb_browser.domain.TvShow

/**
 * Specific implementation of the local data source that uses Room as a provider.
 */
class TvShowRoomDatasource(private val tvShowDao: TvShowDao) : TvShowLocalDatasource {

    override suspend fun getTvShows(size: Int) = tvShowDao.getTvShows(size).toDomain()

    override suspend fun addTvShows(tvShows: List<TvShow>) {
        tvShowDao.addTvShows(tvShows.toRoom())
    }
}