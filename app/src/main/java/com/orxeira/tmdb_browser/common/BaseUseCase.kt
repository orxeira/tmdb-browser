package com.orxeira.tmdb_browser.common

import com.orxeira.tmdb_browser.common.error.ErrorEntity
import com.orxeira.tmdb_browser.common.state.DataState
import com.orxeira.tmdb_browser.common.state.DataState.Companion.errorData
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase {

    protected fun <T> myFlow(
        action: suspend FlowCollector<DataState<T>>.() -> Unit
    ) = flow {
        try {
            this.action()
        } catch (e: Exception) {
            val error = e.message
            emit(
                errorData<T>(
                    ErrorEntity(
                        message = error.orEmpty()
                    )
                )
            )
        }
    }
}