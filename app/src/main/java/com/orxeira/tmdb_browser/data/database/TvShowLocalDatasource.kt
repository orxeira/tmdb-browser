package com.orxeira.tmdb_browser.data.database

import com.orxeira.tmdb_browser.domain.TvShow

interface TvShowLocalDatasource {
    suspend fun getTvShows(size: Int): List<TvShow>

    suspend fun addTvShows(tvShows: List<TvShow>)
}