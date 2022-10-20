package com.movies.data.remote.movies

import com.movies.domain.MoviesResult
import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem

interface RemoteMovies {
    suspend fun getUpcomingMovies(): MoviesResult<List<MovieItem>>
    suspend fun getTopRatedMovies(language: String): MoviesResult<List<MovieItem>>
    suspend fun getMovieDetailById(movieId: Int): MoviesResult<MovieDetail>
}