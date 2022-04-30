package com.orxeira.tmdb_browser.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.orxeira.tmdb_browser.domain.TvShow

@Entity(tableName = "tv_shows")
data class DbTvShow(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Float,
    val releaseDate: String
)

fun DbTvShow.toDomain()= TvShow(
    id,
    name,
    overview,
    posterPath,
    backdropPath,
    voteAverage,
    releaseDate
)

fun TvShow.toRoom() = DbTvShow(
    id,
    name,
    overview,
    posterPath,
    backdropPath,
    voteAverage,
    releaseDate
)