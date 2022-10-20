package com.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movies.data.local.entities.MovieDetailEntity
import com.movies.data.local.entities.MovieItemEntity

@Database(
    entities = [MovieItemEntity::class, MovieDetailEntity::class],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}