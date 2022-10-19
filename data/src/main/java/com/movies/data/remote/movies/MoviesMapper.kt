package com.movies.data.remote.movies

import com.movies.data.remote.api.MovieItemResponse
import com.movies.domain.model.MovieItem

fun List<MovieItemResponse>.transformToMovieItemsList(): List<MovieItem> {
    return map { item ->
        MovieItem(
            id = item.id ?: 0,
            poster = "https://image.tmdb.org/t/p/w500/${item.posterPath}"
        )
    }
}

fun MovieItemResponse.transformToMovieDetail(): MovieItem {
    return MovieItem(
        id = id ?: 0,
        poster = "https://image.tmdb.org/t/p/w500/${posterPath}",
    )
}