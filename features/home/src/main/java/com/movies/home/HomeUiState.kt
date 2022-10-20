package com.movies.home

import com.movies.home.adapter.HomeSection

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class ShowHomeSections(val data: List<HomeSection>) : HomeUiState
    object Error : HomeUiState
}