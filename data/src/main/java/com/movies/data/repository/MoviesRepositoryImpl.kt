package com.movies.data.repository

import com.movies.data.remote.movies.RemoteMovies
import com.movies.domain.MoviesResult
import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem
import com.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteMovies: RemoteMovies,
) : MoviesRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getUpcomingMovies(): MoviesResult<List<MovieItem>> =
        withContext(ioDispatcher) { remoteMovies.getUpcomingMovies() }

    override suspend fun getTopRatedMovies(language: String): MoviesResult<List<MovieItem>> =
        withContext(ioDispatcher) { remoteMovies.getTopRatedMovies(language) }


    override suspend fun getMovieDetailById(movieId: Int): MoviesResult<MovieDetail> =
        withContext(ioDispatcher) { remoteMovies.getMovieDetailById(movieId) }

}