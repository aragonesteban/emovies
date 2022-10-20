package com.movies.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_item")
data class MovieItemEntity(
    @PrimaryKey val id: Int,
    val posterPath: String,
    val section: Int
)