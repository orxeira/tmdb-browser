package com.orxeira.tmdb_browser.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orxeira.tmdb_browser.data.TvShowRepository
import com.orxeira.tmdb_browser.domain.TvShow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailListViewModel(
    private val tvShowRepository: TvShowRepository,
    private val tvShow: TvShow,
) : ViewModel() {

    private val _tvShows = MutableStateFlow(listOf<TvShow>())
    val tvShows: StateFlow<List<TvShow>> = _tvShows.asStateFlow()

    init {
        viewModelScope.launch {
            tvShowRepository.getSimilarShows(tvShow).collectLatest {
                _tvShows.value = it
            }
        }
    }
}
