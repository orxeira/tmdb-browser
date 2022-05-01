package com.orxeira.tmdb_browser.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.orxeira.tmdb_browser.data.PagingRepository
import com.orxeira.tmdb_browser.domain.TvShow
import com.orxeira.tmdb_browser.ui.TvShowState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainViewModel(
    private val pagingRepository: PagingRepository
) : ViewModel() {

    private val _state = MutableStateFlow<TvShowState>(TvShowState.Nothing)
    val state = _state.asStateFlow()

    private val _tvShows = MutableStateFlow<PagingData<TvShow>>(PagingData.empty())
    val tvShows = _tvShows.asStateFlow()

    fun getPopulartvShows() {
        viewModelScope.launch {
            pagingRepository.tvShowPaging.invoke().collectLatest { tvShows ->
                _tvShows.value = tvShows
            }
        }
    }

    fun setTvShow(tvShow: TvShow) {
        _state.value = TvShowState.SuccessTvShow(tvShow)
    }

    fun clearTvShowState() {
        _state.value = TvShowState.Nothing
    }


}