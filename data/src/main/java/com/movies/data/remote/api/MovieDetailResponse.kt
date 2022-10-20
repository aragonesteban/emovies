package com.movies.data.remote.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    val genres: List<MovieGenresResponse>? = null,
    val id: Int? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    val title: String? = null,
    val overview: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    val videos: MovieVideoListResponse? = null
)

@Serializable
data class MovieGenresResponse(
    val name: String? = null
)

@Serializable
data class MovieVideoListResponse(
    val results: List<MovieVideoResponse>? = null
)

@Serializable
data class MovieVideoResponse(
    val key: String? = null,
    val official: Boolean? = false
)