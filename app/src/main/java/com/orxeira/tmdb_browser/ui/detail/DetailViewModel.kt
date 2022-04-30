package com.orxeira.tmdb_browser.ui.detail

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

    private val _tvShows = MutableStateFlow(listOf<TvShow>())
    val tvShows: StateFlow<List<TvShow>> = _tvShows.asStateFlow()

    init {
        viewModelScope.launch {
            _tvShows.value = listOf(tvShow)
        }
    }
}
