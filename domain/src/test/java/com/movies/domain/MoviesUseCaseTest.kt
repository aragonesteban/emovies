package com.movies.domain

import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem
import com.movies.domain.repository.MoviesRepository
import com.movies.domain.usecases.movies.MoviesUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class MoviesUseCaseTest {

    private val movieId = 123
    private val moviesList = listOf(MovieItem(movieId, "test"))
    private val language = "language"
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
    fun `given success result from repository when getUpcomingMovies() is called then MoviesResult will be success`() {
        // given
        val mockMoviesRepository = mockk<MoviesRepository> {
            coEvery { getUpcomingMovies(true) } returns MoviesResult.Success(moviesList)
        }

        val useCase = MoviesUseCaseImpl(mockMoviesRepository)

        runBlocking {
            // when
            val result = useCase.getUpcomingMovies(true)

            // then
            coVerify { mockMoviesRepository.getUpcomingMovies(true) }
            assert(result is MoviesResult.Success)
            assertEquals((result as MoviesResult.Success).data, moviesList)
        }
    }

    @Test
    fun `given error result from repository when getUpcomingMovies() is called then MoviesResult will be error`() {
        // given
        val mockMoviesRepository = mockk<MoviesRepository> {
            coEvery { getUpcomingMovies(true) } returns MoviesResult.Error()
        }

        val useCase = MoviesUseCaseImpl(mockMoviesRepository)

        runBlocking {
            // when
            val result = useCase.getUpcomingMovies(true)

            // then
            coVerify { mockMoviesRepository.getUpcomingMovies(true) }
            assert(result is MoviesResult.Error)
        }
    }

    @Test
    fun `given success result from repository when getTopRatedMovies() is called then MoviesResult will be success`() {
        // given
        val mockMoviesRepository = mockk<MoviesRepository> {
            coEvery { getTopRatedMovies(language, true) } returns MoviesResult.Success(moviesList)
        }

        val useCase = MoviesUseCaseImpl(mockMoviesRepository)

        runBlocking {
            // when
            val result = useCase.getTopRatedMovies(language, true)

            // then
            coVerify { mockMoviesRepository.getTopRatedMovies(language, true) }
            assert(result is MoviesResult.Success)
            assertEquals((result as MoviesResult.Success).data, moviesList)
        }
    }

    @Test
    fun `given error result from repository when getTopRatedMovies() is called then MoviesResult will be error`() {
        // given
        val mockMoviesRepository = mockk<MoviesRepository> {
            coEvery { getTopRatedMovies(language, true) } returns MoviesResult.Error()
        }

        val useCase = MoviesUseCaseImpl(mockMoviesRepository)

        runBlocking {
            // when
            val result = useCase.getTopRatedMovies(language, true)

            // then
            coVerify { mockMoviesRepository.getTopRatedMovies(language, true) }
            assert(result is MoviesResult.Error)
        }
    }

    @Test
    fun `given success result from repository when getMovieDetailById() is called then MoviesResult will be success`() {
        // given
        val mockMoviesRepository = mockk<MoviesRepository> {
            coEvery { getMovieDetailById(movieId) } returns MoviesResult.Success(movieDetail)
        }

        val useCase = MoviesUseCaseImpl(mockMoviesRepository)

        runBlocking {
            // when
            val result = useCase.getMovieDetailById(movieId)

            // then
            coVerify { mockMoviesRepository.getMovieDetailById(movieId) }
            assert(result is MoviesResult.Success)
            assertEquals((result as MoviesResult.Success).data, movieDetail)
        }
    }

    @Test
    fun `given error result from repository when getMovieDetailById() is called then MoviesResult will be error`() {
        // given
        val mockMoviesRepository = mockk<MoviesRepository> {
            coEvery { getMovieDetailById(movieId) } returns MoviesResult.Error()
        }

        val useCase = MoviesUseCaseImpl(mockMoviesRepository)

        runBlocking {
            // when
            val result = useCase.getMovieDetailById(movieId)

            // then
            coVerify { mockMoviesRepository.getMovieDetailById(movieId) }
            assert(result is MoviesResult.Error)
        }
    }

}