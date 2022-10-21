package com.movies.domain.usecases.movies

import com.movies.domain.MoviesResult
import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem
import com.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : MoviesUseCase {
    override suspend fun getUpcomingMovies(): MoviesResult<List<MovieItem>> =
        moviesRepository.getUpcomingMovies()

    override suspend fun getTopRatedMovies(language: String, isOnline: Boolean): MoviesResult<List<MovieItem>> =
        moviesRepository.getTopRatedMovies(language, isOnline)

    override suspend fun getMovieDetailById(movieId: Int): MoviesResult<MovieDetail> =
        moviesRepository.getMovieDetailById(movieId)
}