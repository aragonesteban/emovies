package com.movies.data.remote.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val results: List<MovieItemResponse> = listOf()
)

@Serializable
data class MovieItemResponse(
    val id: Int? = null,
    @SerialName("poster_path")
    val posterPath: String? = null
) {

}