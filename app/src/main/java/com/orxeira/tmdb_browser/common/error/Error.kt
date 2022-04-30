package com.orxeira.tmdb_browser.common.error

const val EMPTY_STRING = ""

abstract class Error

data class ErrorEntity(
    val message: String = EMPTY_STRING,
    val code: String = EMPTY_STRING,
): Error()