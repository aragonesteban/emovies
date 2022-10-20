package com.movies.data.repository

import com.movies.data.local.movies.LocalMovies
import com.movies.data.remote.movies.RemoteMovies
import com.movies.domain.MoviesResult
import com.movies.domain.TOP_RATED_MOVIES_SECTION
import com.movies.domain.UP_COMING_MOVIES_SECTION
import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem
import com.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteMovies: RemoteMovies,
    private val localMovies: LocalMovies
) : MoviesRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getUpcomingMovies(): MoviesResult<List<MovieItem>> {
        return withContext(ioDispatcher) {
            handleResultGetMovies(UP_COMING_MOVIES_SECTION) {
                remoteMovies.getUpcomingMovies()
            }
        }
    }


    override suspend fun getTopRatedMovies(language: String): MoviesResult<List<MovieItem>> {
        return withContext(ioDispatcher) {
            handleResultGetMovies(TOP_RATED_MOVIES_SECTION) {
                remoteMovies.getTopRatedMovies(language)
            }
        }
    }

    private suspend fun handleResultGetMovies(
        section: Int,
        remoteCall: suspend () -> MoviesResult<List<MovieItem>>
    ): MoviesResult<List<MovieItem>> {
        val moviesList = localMovies.getMoviesBySection(section)
        return if (moviesList.isNotEmpty()) {
            MoviesResult.Success(moviesList)
        } else {
            val result = remoteCall()
            if (result is MoviesResult.Success) {
                localMovies.insertMoviesItem(result.data, section)
            }
            result
        }
    }

    override suspend fun getMovieDetailById(movieId: Int): MoviesResult<MovieDetail> {
        return withContext(ioDispatcher) {
            val movieDetail = localMovies.getMovieDetailById(movieId)
            movieDetail?.let {
                MoviesResult.Success(movieDetail)
            } ?: run {
                val result = remoteMovies.getMovieDetailById(movieId)
                if (result is MoviesResult.Success) {
                    localMovies.insertMovieDetail(result.data)
                }
                result
            }
        }
    }
}