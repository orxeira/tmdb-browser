package com.orxeira.tmdb_browser.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orxeira.tmdb_browser.usecases.GetSimilarShowsUseCase
import com.orxeira.tmdb_browser.domain.TvShow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Very simple ViewModel with a flow and a use case. The use case is called on init and the result
 * updates the flow. In this case, the ViewModel receives the tvShow as a parameter.
 */
class DetailListViewModel(
    private val getSimilarShowsUseCase: GetSimilarShowsUseCase,
    private val tvShow: TvShow,
) : ViewModel() {

    private val _tvShows = MutableStateFlow(listOf<TvShow>())
    val tvShows: StateFlow<List<TvShow>> = _tvShows.asStateFlow()

    init {
        viewModelScope.launch {
            getSimilarShowsUseCase(tvShow).collectLatest {
                _tvShows.value = it
            }
        }
    }
}
