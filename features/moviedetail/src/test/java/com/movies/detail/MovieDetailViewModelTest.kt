package com.movies.detail

import com.movies.domain.MoviesResult
import com.movies.domain.model.MovieDetail
import com.movies.domain.usecases.movies.MoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val movieId = 123
    private val movieDetail = MovieDetail(
        genres = "",
        id = movieId,
        originalLanguage = "",
        title = "",
        overview = "",
        posterPath = "",
        releaseDate = "",
        voteAverage = 8,
        videoYoutubeKey = "",
    )

    @Test
    fun `given movieId and success response when getMovieDetail is called then state emit ShowMovieDetail`() {
        // given
        val mockMoviesUseCase = mockk<MoviesUseCase> {
            coEvery { getMovieDetailById(movieId) } returns MoviesResult.Success(movieDetail)
        }

        // when
        val viewModel = MovieDetailViewModel(mockMoviesUseCase)
        viewModel.getMovieDetail(movieId)

        // then
        assertTrue(viewModel.uiState.value is MovieDetailUiState.ShowMovieDetail)
        assertEquals(
            (viewModel.uiState.value as MovieDetailUiState.ShowMovieDetail).data,
            movieDetail
        )
        coVerify { mockMoviesUseCase.getMovieDetailById(movieId) }
    }

    @Test
    fun `given movieId and error response when getMovieDetail is called then state emit Error`() {
        // given
        val mockMoviesUseCase = mockk<MoviesUseCase> {
            coEvery { getMovieDetailById(movieId) } returns MoviesResult.Error()
        }

        // when
        val viewModel = MovieDetailViewModel(mockMoviesUseCase)
        viewModel.getMovieDetail(movieId)

        // then
        assertTrue(viewModel.uiState.value is MovieDetailUiState.Error)
        coVerify { mockMoviesUseCase.getMovieDetailById(movieId) }
    }

}