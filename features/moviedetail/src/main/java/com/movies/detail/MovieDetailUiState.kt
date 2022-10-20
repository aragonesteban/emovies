package com.movies.detail

import com.movies.domain.model.MovieDetail

sealed interface MovieDetailUiState {
    object Loading : MovieDetailUiState
    data class ShowMovieDetail(val data: MovieDetail) : MovieDetailUiState
    object Error : MovieDetailUiState
}