package com.movies.data.remote.movies

import com.movies.data.remote.api.MoviesApi
import com.movies.data.remote.api.executeRetrofitRequest
import com.movies.data.remote.api.handleResultRetrofit
import com.movies.domain.MoviesResult
import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem
import javax.inject.Inject

class RemoteMoviesImpl @Inject constructor(
    private val moviesApi: MoviesApi
) : RemoteMovies {

    override suspend fun getUpcomingMovies(): MoviesResult<List<MovieItem>> {
        val result = executeRetrofitRequest { moviesApi.getUpcomingMovies() }
        return handleResultRetrofit(result) { it.results.transformToMovieItemsList() }
    }

    override suspend fun getTopRatedMovies(language: String): MoviesResult<List<MovieItem>> {
        val result = executeRetrofitRequest { moviesApi.getTopRatedMovies(language = language) }
        return handleResultRetrofit(result) { it.results.transformToMovieItemsList() }
    }

    override suspend fun getMovieDetailById(movieId: Int): MoviesResult<MovieDetail> {
        val result = executeRetrofitRequest { moviesApi.getMovieDetailById(movieId = movieId) }
        return handleResultRetrofit(result) { it.transformToMovieDetail() }
    }

}