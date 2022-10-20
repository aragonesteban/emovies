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

internal const val UP_COMING_MOVIES_SECTION = 0
internal const val TOP_RATED_MOVIES_SECTION = 1
internal const val RECOMMENDED_MOVIES_SECTION = 2
private const val MAXIMUM_RECOMMENDED_MOVIES = 6

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private var _homeSectionsList = listOf<HomeSection>()

    fun setHomeSectionsList(value: List<HomeSection>) {
        _uiState.value = if (_homeSectionsList.isNotEmpty()) {
            HomeUiState.ShowHomeSections(_homeSectionsList)
        } else {
            getUpComingMovies()
            getTopRatedMovies()
            getRecommendedMovies(ES_CO)
            _homeSectionsList = value
            HomeUiState.ShowHomeSections(_homeSectionsList)
        }
    }

    fun getUpComingMovies() {
        viewModelScope.launch {
            _uiState.value = handleResult(moviesUseCase.getUpcomingMovies()) { data ->
                _homeSectionsList = updateHomeSectionsList(UP_COMING_MOVIES_SECTION, data)
                HomeUiState.ShowHomeSections(_homeSectionsList)
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            _uiState.value =
                handleResult(moviesUseCase.getTopRatedMovies(language = EN_US)) { data ->
                    _homeSectionsList = updateHomeSectionsList(TOP_RATED_MOVIES_SECTION, data)
                    HomeUiState.ShowHomeSections(_homeSectionsList)
                }
        }
    }

    fun getRecommendedMovies(language: String) {
        viewModelScope.launch {
            _uiState.value = handleResult(moviesUseCase.getTopRatedMovies(language)) { data ->
                val recommendedMovies = data.take(MAXIMUM_RECOMMENDED_MOVIES)
                _homeSectionsList =
                    updateHomeSectionsList(RECOMMENDED_MOVIES_SECTION, recommendedMovies)
                HomeUiState.ShowHomeSections(_homeSectionsList)
            }
        }
    }

    private fun updateHomeSectionsList(
        section: Int,
        value: List<MovieItem>
    ): List<HomeSection> {
        return _homeSectionsList.mapIndexed { index, item ->
            if (index == section) {
                item.copy(isLoading = false, data = value)
            } else {
                item
            }
        }
    }

    private fun <T : Any> handleResult(
        result: MoviesResult<T>,
        onSuccess: (T) -> HomeUiState
    ): HomeUiState {
        return when (result) {
            is MoviesResult.Success -> onSuccess.invoke(result.data)
            else -> HomeUiState.Error
        }
    }

}