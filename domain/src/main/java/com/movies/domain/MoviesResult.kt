package com.movies.domain

sealed interface MoviesResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : MoviesResult<T>
    data class Error(val message: String? = String(), val code: Int? = null) : MoviesResult<Nothing>
}