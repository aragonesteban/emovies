package com.movies.domain.model

data class MovieDetail(
    val genres: String,
    val id: Int,
    val originalLanguage: String,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Int,
    val videoYoutubeKey: String
)