package com.orxeira.tmdb_browser.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.orxeira.tmdb_browser.usecases.GetTvShowPagingUseCase
import com.orxeira.tmdb_browser.domain.TvShow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Very simple ViewModel that receives a use case and executes it under demand.
 * The result of the use case is a Flow with the PagingData necessary for the Adapter in the view.
 */
class MainViewModel(
    private val getTvShowPagingUseCase: GetTvShowPagingUseCase
) : ViewModel() {

    private val _tvShows = MutableStateFlow<PagingData<TvShow>>(PagingData.empty())
    val tvShows = _tvShows.asStateFlow()


    /**
     * This method is called by the view and calls the useCase to obtain a PagingData. The result is
     * set on a Flow.
     */
    fun getPopulartvShows() {
        viewModelScope.launch {
            getTvShowPagingUseCase().collectLatest { tvShows ->
                _tvShows.value = tvShows
            }
        }
    }
}