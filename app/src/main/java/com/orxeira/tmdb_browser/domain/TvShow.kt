package com.orxeira.tmdb_browser.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Float,
    val releaseDate: String
): Parcelable