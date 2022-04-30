package com.orxeira.tmdb_browser.data

import com.orxeira.tmdb_browser.common.BaseUseCase

class TvShowPaging (
private val repository: TvShowRepository
): BaseUseCase() {

    suspend operator fun invoke() = repository.getTvShowPaging()

}