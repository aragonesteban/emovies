package com.movies.detail

import com.movies.domain.model.MovieDetail

sealed interface MovieDetailUiState {
    object Loading : MovieDetailUiState
    data class ShowMovieDetail(val data: MovieDetail) : MovieDetailUiState
    data class ShowMovieTrailer(val videoYoutubeKey: String) : MovieDetailUiState
    object Error : MovieDetailUiState
}