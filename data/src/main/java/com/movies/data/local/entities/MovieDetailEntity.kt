package com.movies.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_detail")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    val genres: String,
    val originalLanguage: String,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Int,
    val videoYoutubeKey: String
)