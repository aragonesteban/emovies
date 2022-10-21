package com.movies.home

import com.movies.domain.MoviesResult
import com.movies.domain.usecases.movies.MoviesUseCase
import com.movies.home.adapter.HomeSection
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockMoviesUseCase = mockk<MoviesUseCase>(relaxed = true)

    private val homeSectionsList = listOf(
        HomeSection(
            id = 0,
            title = "test",
        )
    )

    @Test
    fun `given home sections list when setHomeSectionsList is called then state emit ShowHomeSections`() {
        // given
        val viewModel = HomeViewModel(mockMoviesUseCase)

        // when
        viewModel.setHomeSectionsList(homeSectionsList, true)

        // then
        assertTrue(viewModel.uiState.value is HomeUiState.ShowHomeSections)
        assertEquals(
            (viewModel.uiState.value as HomeUiState.ShowHomeSections).data,
            homeSectionsList
        )
        verify { viewModel.getUpComingMovies(true) }
        verify { viewModel.getTopRatedMovies(true) }
        verify { viewModel.getRecommendedMovies(ES_CO, true) }
    }

    @Test
    fun `given error as result when getUpComingMovies is called then state emit Error`() {
        // given
        val mockMoviesUseCase = mockk<MoviesUseCase> {
            coEvery { getUpcomingMovies(true) } returns MoviesResult.Error()
        }
        val viewModel = HomeViewModel(mockMoviesUseCase)

        // when
        viewModel.getUpComingMovies(true)

        // then
        assertTrue(viewModel.uiState.value is HomeUiState.Error)
    }

    @Test
    fun `given error as result when getTopRatedMovies is called then state emit Error`() {
        // given
        val mockMoviesUseCase = mockk<MoviesUseCase> {
            coEvery { getTopRatedMovies(EN_US, true) } returns MoviesResult.Error()
        }
        val viewModel = HomeViewModel(mockMoviesUseCase)

        // when
        viewModel.getTopRatedMovies(true)

        // then
        assertTrue(viewModel.uiState.value is HomeUiState.Error)
    }

    @Test
    fun `given error as result when getRecommendedMovies is called then state emit Error`() {
        // given
        val mockMoviesUseCase = mockk<MoviesUseCase> {
            coEvery { getTopRatedMovies(EN_US, true) } returns MoviesResult.Error()
        }
        val viewModel = HomeViewModel(mockMoviesUseCase)

        // when
        viewModel.getRecommendedMovies(EN_US, true)

        // then
        assertTrue(viewModel.uiState.value is HomeUiState.Error)
    }

}