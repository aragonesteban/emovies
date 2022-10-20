package com.movies.data.local.movies

import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem

interface LocalMovies {
    fun insertMoviesItem(moviesItemList: List<MovieItem>, section: Int)
    fun insertMovieDetail(movieDetail: MovieDetail)
    fun getMovieDetailById(movieId: Int): MovieDetail?
    fun getMoviesBySection(section: Int): List<MovieItem>
}