package com.movies.domain.repository

import com.movies.domain.MoviesResult
import com.movies.domain.model.MovieItem

interface MoviesRepository {
    suspend fun getUpcomingMovies(): MoviesResult<List<MovieItem>>
    suspend fun getTopRatedMovies(language: String): MoviesResult<List<MovieItem>>
    suspend fun getMovieDetailById(movieId: Int): MoviesResult<MovieItem>
}