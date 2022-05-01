package com.orxeira.tmdb_browser.ui

import com.orxeira.tmdb_browser.domain.TvShow

sealed class TvShowState {
    object Loading : TvShowState()
    object Success : TvShowState()
    object Nothing : TvShowState()
    data class SuccessTvShow(val data: TvShow) : TvShowState()
    data class Error(val error: com.orxeira.tmdb_browser.common.error.Error) : TvShowState()
}