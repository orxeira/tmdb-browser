package com.orxeira.tmdb_browser.common

import com.orxeira.tmdb_browser.domain.TvShow
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class TvShowFactory {
    private val counter = AtomicInteger(0)
    fun createTvShow(): TvShow {
        val id = counter.incrementAndGet()
        return TvShow(
            id = id,
            name = "name_$id",
            overview = "overview $id",
            posterPath = "posterPath $id",
            backdropPath = "backdropPath $id",
            voteAverage = Random.nextInt(0, 5).toFloat(),
            releaseDate = "date $id",
        )
    }
}