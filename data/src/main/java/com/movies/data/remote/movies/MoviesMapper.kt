package com.movies.data.remote.movies

import com.movies.data.remote.api.MovieDetailResponse
import com.movies.data.remote.api.MovieItemResponse
import com.movies.data.remote.api.MovieVideoResponse
import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem
import kotlin.math.abs

fun List<MovieItemResponse>.transformToMovieItemsList(): List<MovieItem> {
    return map { item ->
        MovieItem(
            id = item.id ?: 0,
            poster = "https://image.tmdb.org/t/p/w500${item.posterPath}"
        )
    }
}

fun MovieDetailResponse.transformToMovieDetail(): MovieDetail {
    return MovieDetail(
        genres = genres?.joinToString(separator = " • ") { it.name.toString() }.orEmpty(),
        id = id ?: 0,
        originalLanguage = originalLanguage ?: String(),
        title = title ?: String(),
        overview = overview ?: String(),
        posterPath = "https://image.tmdb.org/t/p/original$posterPath",
        releaseDate = releaseDate ?: String(),
        voteAverage = abs(voteAverage ?: 0.0).toInt(),
        videoYoutubeKey = setVideoYoutubeKey(videos?.results)
    )
}

private fun setVideoYoutubeKey(results: List<MovieVideoResponse>?): String {
    return if (results?.isNotEmpty() == true) {
        val officialTrailers = results.filter { it.official == true }
        if (officialTrailers.isNotEmpty()) {
            officialTrailers.first().key.toString()
        } else {
            String()
        }
    } else {
        String()
    }
}