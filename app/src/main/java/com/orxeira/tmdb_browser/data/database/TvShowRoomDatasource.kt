package com.orxeira.tmdb_browser.data.database

class TvShowRoomDatasource(private val tvShowDao: TvShowDao) : TvShowLocalDatasource {

    override suspend fun getTvShows(size: Int) = tvShowDao.getTvShows(size)

    override suspend fun addTvShows(tvShows: List<DbTvShow>) {
        tvShowDao.addTvShows(tvShows)
    }
}