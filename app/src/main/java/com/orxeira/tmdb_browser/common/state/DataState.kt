package com.orxeira.tmdb_browser.common.state

import com.orxeira.tmdb_browser.common.error.Error

data class DataState<T>(
    val error: Error? = null,
    val data: T? = null,
    val isLoading: Boolean = false,
) {

    companion object {

        fun <T> errorData(error: Error): DataState<T> = DataState(error = error)

        fun <T> data(data: T? = null): DataState<T> = DataState(data = data)

        fun <T> loading() = DataState<T>(isLoading = true)

    }
}