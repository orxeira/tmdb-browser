package com.orxeira.tmdb_browser.data.usecases

import com.orxeira.tmdb_browser.data.repositories.TvShowRepository

class GetTvShowPagingUseCase(private val repository: TvShowRepository) {
    operator fun invoke() = repository.getTvShowPaging()
}