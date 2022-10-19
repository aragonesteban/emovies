package com.movies.home

import com.movies.domain.model.MovieItem

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class ShowUpcomingMovies(val data: List<MovieItem>) : HomeUiState
    data class ShowTopRatedMovies(val data: List<MovieItem>) : HomeUiState
    data class ShowRecommendedMovies(val data: List<MovieItem>) : HomeUiState
    object Error : HomeUiState
}