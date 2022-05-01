package com.orxeira.tmdb_browser.data.database

/**
 * Specific implementation of the local data source that uses Room as a provider.
 */
class TvShowRoomDatasource(private val tvShowDao: TvShowDao) : TvShowLocalDatasource {

    override suspend fun getTvShows(size: Int) = tvShowDao.getTvShows(size)

    override suspend fun addTvShows(tvShows: List<DbTvShow>) {
        tvShowDao.addTvShows(tvShows)
    }
}