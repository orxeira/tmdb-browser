package com.orxeira.tmdb_browser.ui.detail.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orxeira.tmdb_browser.domain.TvShow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class DetailViewModel(
    private val tvShow: TvShow
) : ViewModel() {

    private val _tvShow = MutableStateFlow<TvShow?>(null)
    val tvShows: StateFlow<TvShow?> = _tvShow.asStateFlow()

    init {
        viewModelScope.launch {
            _tvShow.value = tvShow
        }
    }
}