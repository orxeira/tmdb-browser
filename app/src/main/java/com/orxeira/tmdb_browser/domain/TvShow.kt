package com.orxeira.tmdb_browser.domain

import com.orxeira.tmdb_browser.data.database.DbTvShow

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Float,
)