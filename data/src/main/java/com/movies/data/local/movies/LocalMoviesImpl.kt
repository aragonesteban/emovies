package com.movies.data.local.movies

import com.movies.data.local.MoviesDao
import com.movies.data.local.entities.MovieDetailEntity
import com.movies.data.local.entities.MovieItemEntity
import com.movies.domain.model.MovieDetail
import com.movies.domain.model.MovieItem
import javax.inject.Inject

class LocalMoviesImpl @Inject constructor(
    private val moviesDao: MoviesDao
) : LocalMovies {

    override fun insertMoviesItem(moviesItemList: List<MovieItem>, section: Int) {
        val moviesItemsEntityList = moviesItemList.map { movie ->
            MovieItemEntity(
                id = movie.id,
                posterPath = movie.poster,
                section = section
            )
        }
        moviesDao.insertMoviesItem(moviesItemsEntityList)
    }

    override fun insertMovieDetail(movieDetail: MovieDetail) {
        val movieDetailEntity = MovieDetailEntity(
            id = movieDetail.id,
            genres = movieDetail.genres,
            originalLanguage = movieDetail.originalLanguage,
            title = movieDetail.title,
            overview = movieDetail.overview,
            posterPath = movieDetail.posterPath,
            releaseDate = movieDetail.releaseDate,
            voteAverage = movieDetail.voteAverage,
            videoYoutubeKey = movieDetail.videoYoutubeKey,
        )
        moviesDao.insertMovieDetail(movieDetailEntity)
    }

    override fun getMovieDetailById(movieId: Int): MovieDetail? {
        val movieDetailEntity = moviesDao.getMovieDetailById(movieId)
        return movieDetailEntity?.run {
            MovieDetail(
                id = id,
                genres = genres,
                originalLanguage = originalLanguage,
                title = title,
                overview = overview,
                posterPath = posterPath,
                releaseDate = releaseDate,
                voteAverage = voteAverage,
                videoYoutubeKey = videoYoutubeKey,
            )
        }
    }

    override fun getMoviesBySection(section: Int): List<MovieItem> {
        return moviesDao.getMoviesBySection(section).map { movie ->
            MovieItem(
                id = movie.id,
                poster = movie.posterPath
            )
        }
    }

}