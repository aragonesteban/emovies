package com.movies.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.domain.MoviesResult
import com.movies.domain.model.MovieItem
import com.movies.domain.usecases.movies.MoviesUseCase
import com.movies.home.adapter.HomeSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MAXIMUM_RECOMMENDED_MOVIES = 6

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private var _upComingMovies = listOf<MovieItem>()
    private var _topRatedMovies = listOf<MovieItem>()
    private var _recommendedMovies = listOf<MovieItem>()

    fun getUpComingMovies() {
        viewModelScope.launch {
            _uiState.value = when (val result = moviesUseCase.getUpcomingMovies()) {
                is MoviesResult.Success -> {
                    _upComingMovies = result.data
                    HomeUiState.ShowUpcomingMovies(result.data)
                }
                is MoviesResult.Error -> HomeUiState.Error
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            _uiState.value = when (val result = moviesUseCase.getTopRatedMovies(language = EN_US)) {
                is MoviesResult.Success -> {
                    _topRatedMovies = result.data
                    HomeUiState.ShowTopRatedMovies(_topRatedMovies)
                }
                is MoviesResult.Error -> HomeUiState.Error
            }
        }
    }

    fun getRecommendedMovies(language: String) {
        viewModelScope.launch {
            _uiState.value = when (val result = moviesUseCase.getTopRatedMovies(language)) {
                is MoviesResult.Success -> {
                    _recommendedMovies = result.data.take(MAXIMUM_RECOMMENDED_MOVIES)
                    HomeUiState.ShowRecommendedMovies(_recommendedMovies)
                }
                is MoviesResult.Error -> HomeUiState.Error
            }
        }
    }

    fun updateHomeSectionsList(
        homeSectionsHomeList: List<HomeSection>,
        section: Int,
        value: List<MovieItem>
    ): List<HomeSection> {
        return homeSectionsHomeList.mapIndexed { index, item ->
            if (index == section) {
                item.copy(isLoading = false, data = value)
            } else {
                item
            }
        }
    }
}