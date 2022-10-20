package com.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.data.local.entities.MovieDetailEntity
import com.movies.data.local.entities.MovieItemEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesItem(moviesItemList: List<MovieItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(movieDetail: MovieDetailEntity)

    @Query("SELECT * FROM movies_detail WHERE id = :movieId")
    fun getMovieDetailById(movieId: Int): MovieDetailEntity?

    @Query("SELECT * FROM movies_item WHERE section = :section")
    fun getMoviesBySection(section: Int): List<MovieItemEntity>

}