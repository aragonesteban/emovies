package com.movies.data

import com.movies.data.local.movies.LocalMovies
import com.movies.data.remote.movies.RemoteMovies
import com.movies.data.repository.MoviesRepositoryImpl
import com.movies.domain.MoviesResult
import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class MoviesRepositoryTest {

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

    private val mockRemoteMovies = mockk<RemoteMovies>(relaxed = true)
    private val mockLocalMovies = mockk<LocalMovies>(relaxed = true)

    @Test
    fun `given success response when getUpcomingMovies() is called then response is saved in cache and response is success`() {
        // given
        val mockRemoteMovies = mockk<RemoteMovies> {
            coEvery { getUpcomingMovies() } returns MoviesResult.Success(moviesList)
        }
        val mockLocalMovies = mockk<LocalMovies> {
            every { insertMoviesItem(moviesList, 0) } returns mockk(relaxed = true)
            every { getMoviesBySection(0) } returns listOf()
        }

        val repository = MoviesRepositoryImpl(mockRemoteMovies, mockLocalMovies)

        runBlocking {
            // when
            val result = repository.getUpcomingMovies(true)

            // then
            coVerify { mockRemoteMovies.getUpcomingMovies() }
            coVerify { mockLocalMovies.insertMoviesItem(moviesList, 0) }
            assert(result is MoviesResult.Success)
            assertEquals((result as MoviesResult.Success).data, moviesList)
        }
    }

    @Test
    fun `given data in local when getUpcomingMovies() is called then cache is called in return data saved`() {
        // given
        val mockLocalMovies = mockk<LocalMovies> {
            every { getMoviesBySection(0) } returns moviesList
        }

        val repository = MoviesRepositoryImpl(mockRemoteMovies, mockLocalMovies)

        runBlocking {
            // when
            val result = repository.getUpcomingMovies(false)

            // given
            coVerify { mockLocalMovies.getMoviesBySection(0) }
            assert(result is MoviesResult.Success)
            assertEquals((result as MoviesResult.Success).data, moviesList)
        }
    }

    @Test
    fun `given error response when getUpcomingMovies() is called then return is Error`() {
        // given
        val mockRemoteMovies = mockk<RemoteMovies> {
            coEvery { getUpcomingMovies() } returns MoviesResult.Error()
        }

        val repository = MoviesRepositoryImpl(mockRemoteMovies, mockLocalMovies)

        runBlocking {
            // when
            val result = repository.getUpcomingMovies(true)

            // then
            coVerify { mockRemoteMovies.getUpcomingMovies() }
            assert(result is MoviesResult.Error)
        }
    }

    @Test
    fun `given success response when getTopRatedMovies() is called then response is saved in cache and response is success`() {
        // given
        val mockRemoteMovies = mockk<RemoteMovies> {
            coEvery { getTopRatedMovies(language) } returns MoviesResult.Success(moviesList)
        }
        val mockLocalMovies = mockk<LocalMovies> {
            every { insertMoviesItem(moviesList, 1) } returns mockk(relaxed = true)
            every { getMoviesBySection(1) } returns listOf()
        }

        val repository = MoviesRepositoryImpl(mockRemoteMovies, mockLocalMovies)

        runBlocking {
            // when
            val result = repository.getTopRatedMovies(language, true)

            // then
            coVerify { mockRemoteMovies.getTopRatedMovies(language) }
            coVerify { mockLocalMovies.insertMoviesItem(moviesList, 1) }
            assert(result is MoviesResult.Success)
            assertEquals((result as MoviesResult.Success).data, moviesList)
        }
    }

    @Test
    fun `given data in local when getTopRatedMovies() is called then cache is called in return data saved`() {
        // given
        val mockLocalMovies = mockk<LocalMovies> {
            every { getMoviesBySection(1) } returns moviesList
        }

        val repository = MoviesRepositoryImpl(mockRemoteMovies, mockLocalMovies)

        runBlocking {
            // when
            val result = repository.getTopRatedMovies(language, false)

            // given
            coVerify { mockLocalMovies.getMoviesBySection(1) }
            assert(result is MoviesResult.Success)
            assertEquals((result as MoviesResult.Success).data, moviesList)
        }
    }

    @Test
    fun `given error response when getTopRatedMovies() is called then return is Error`() {
        // given
        val mockRemoteMovies = mockk<RemoteMovies> {
            coEvery { getTopRatedMovies(language) } returns MoviesResult.Error()
        }

        val repository = MoviesRepositoryImpl(mockRemoteMovies, mockLocalMovies)

        runBlocking {
            // when
            val result = repository.getTopRatedMovies(language, true)

            // then
            coVerify { mockRemoteMovies.getTopRatedMovies(language) }
            assert(result is MoviesResult.Error)
        }
    }

    @Test
    fun `given success response when getMovieDetailById() is called then response is saved in cache and response is success`() {
        // given
        val mockRemoteMovies = mockk<RemoteMovies> {
            coEvery { getMovieDetailById(movieId) } returns MoviesResult.Success(movieDetail)
        }
        val mockLocalMovies = mockk<LocalMovies> {
            every { insertMovieDetail(movieDetail) } returns mockk(relaxed = true)
            every { getMovieDetailById(movieId) } returns null
        }

        val repository = MoviesRepositoryImpl(mockRemoteMovies, mockLocalMovies)

        runBlocking {
            // when
            val result = repository.getMovieDetailById(movieId)

            // then
            coVerify { mockRemoteMovies.getMovieDetailById(movieId) }
            coVerify { mockLocalMovies.insertMovieDetail(movieDetail) }
            assert(result is MoviesResult.Success)
            assertEquals((result as MoviesResult.Success).data, movieDetail)
        }
    }

    @Test
    fun `given data in local when getMovieDetailById() is called then cache is called in return data saved`() {
        // given
        val mockLocalMovies = mockk<LocalMovies> {
            every { getMovieDetailById(movieId) } returns movieDetail
        }

        val repository = MoviesRepositoryImpl(mockRemoteMovies, mockLocalMovies)

        runBlocking {
            // when
            val result = repository.getMovieDetailById(movieId)

            // given
            coVerify { mockLocalMovies.getMovieDetailById(movieId) }
            assert(result is MoviesResult.Success)
            assertEquals((result as MoviesResult.Success).data, movieDetail)
        }
    }

    @Test
    fun `given error response when getMovieDetailById() is called then return is Error`() {
        // given
        val mockRemoteMovies = mockk<RemoteMovies> {
            coEvery { getMovieDetailById(movieId) } returns MoviesResult.Error()
        }
        val mockLocalMovies = mockk<LocalMovies> {
            every { getMovieDetailById(movieId) } returns null
        }

        val repository = MoviesRepositoryImpl(mockRemoteMovies, mockLocalMovies)

        runBlocking {
            // when
            val result = repository.getMovieDetailById(movieId)

            // then
            coVerify { mockRemoteMovies.getMovieDetailById(movieId) }
            assert(result is MoviesResult.Error)
        }
    }

}