package com.orxeira.tmdb_browser.data.database

interface TvShowLocalDatasource {
    suspend fun getTvShows(size: Int): List<DbTvShow>

    suspend fun addTvShows(tvShows: List<DbTvShow>)
}