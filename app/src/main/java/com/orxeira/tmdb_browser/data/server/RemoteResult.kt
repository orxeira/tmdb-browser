package com.orxeira.tmdb_browser.data.server

import com.google.gson.annotations.SerializedName
import com.orxeira.tmdb_browser.domain.TvShow

data class RemoteResult(
    val page: Int,
    val results: List<RemoteTvShow>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class RemoteTvShow(
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("first_air_date") val firstAirDate: String,
)

fun List<RemoteTvShow>.toDomainModel(): List<TvShow> =
    map { it.toDomainModel() }

fun RemoteTvShow.toDomainModel(): TvShow =
    TvShow(
        id,
        name,
        overview,
        "https://image.tmdb.org/t/p/w185/$posterPath",
        "https://image.tmdb.org/t/p/w780/${backdropPath ?: posterPath}",
        voteAverage
    )