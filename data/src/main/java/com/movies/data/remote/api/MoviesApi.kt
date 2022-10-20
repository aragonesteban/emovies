package com.movies.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY_VALUE = "dc5ceb4ee432272da39ec411f4078888"
private const val API_KEY = "api_key"
private const val LANGUAGE = "language"
private const val DEFAULT_LANGUAGE = "en-US"
private const val UPCOMING = "upcoming"
private const val TOP_RATED = "top_rated"
private const val MOVIE_ID = "movieId"
private const val APPEND_TO_RESPONSE = "append_to_response"
private const val VIDEOS = "videos"

interface MoviesApi {

    @GET(UPCOMING)
    suspend fun getUpcomingMovies(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE
    ): Response<MoviesResponse>

    @GET(TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query(API_KEY) apikey: String = API_KEY_VALUE,
        @Query(LANGUAGE) language: String = DEFAULT_LANGUAGE
    ): Response<MoviesResponse>

    @GET("{$MOVIE_ID}")
    suspend fun getMovieDetailById(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) apikey: String = API_KEY_VALUE,
        @Query(APPEND_TO_RESPONSE) appendToResponse: String = VIDEOS
    ): Response<MovieDetailResponse>

}