package com.movies.domain.usecases.movies

import com.movies.domain.MoviesResult
import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem

interface MoviesUseCase {
    suspend fun getUpcomingMovies(): MoviesResult<List<MovieItem>>

    suspend fun getTopRatedMovies(
        language: String,
        isOnline: Boolean
    ): MoviesResult<List<MovieItem>>

    suspend fun getMovieDetailById(movieId: Int): MoviesResult<MovieDetail>
}