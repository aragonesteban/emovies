package com.movies.domain.repository

import com.movies.domain.MoviesResult
import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem

interface MoviesRepository {
    suspend fun getUpcomingMovies(isOnline: Boolean): MoviesResult<List<MovieItem>>
    suspend fun getTopRatedMovies(language: String, isOnline: Boolean): MoviesResult<List<MovieItem>>
    suspend fun getMovieDetailById(movieId: Int): MoviesResult<MovieDetail>
}