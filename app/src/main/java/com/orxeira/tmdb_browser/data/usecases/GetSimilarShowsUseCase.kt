package com.orxeira.tmdb_browser.data.usecases

import com.orxeira.tmdb_browser.data.repositories.TvShowRepository
import com.orxeira.tmdb_browser.domain.TvShow

class GetSimilarShowsUseCase(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(tvShow: TvShow) = tvShowRepository.getSimilarShows(tvShow)
}