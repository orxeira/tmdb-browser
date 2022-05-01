package com.orxeira.tmdb_browser.data.server

import com.orxeira.tmdb_browser.domain.TvShow

interface TvShowRemoteDataSource {
    suspend fun getTopRatedTvShows(page: Int): List<TvShow>

    suspend fun getSimilarShows(id: Int): List<TvShow>
}