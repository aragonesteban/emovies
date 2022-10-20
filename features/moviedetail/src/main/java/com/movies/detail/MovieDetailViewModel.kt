package com.movies.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.domain.MoviesResult
import com.movies.domain.usecases.movies.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = when (val result = moviesUseCase.getMovieDetailById(movieId)) {
                is MoviesResult.Success -> MovieDetailUiState.ShowMovieDetail(result.data)
                is MoviesResult.Error -> MovieDetailUiState.Error
            }
        }
    }

}