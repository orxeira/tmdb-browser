package com.orxeira.tmdb_browser.ui

import com.orxeira.tmdb_browser.domain.TvShow

sealed class TvShowState {
    object Loading : TvShowState()
    object Nothing : TvShowState()
    data class SuccessTvShow(val data: TvShow) : TvShowState()
}