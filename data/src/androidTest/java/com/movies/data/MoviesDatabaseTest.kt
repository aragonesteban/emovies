package com.movies.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.movies.data.local.MoviesDao
import com.movies.data.local.MoviesDatabase
import com.movies.data.local.entities.MovieDetailEntity
import com.movies.data.local.entities.MovieItemEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MoviesDatabaseTest {

    private lateinit var database: MoviesDatabase
    private lateinit var moviesDao: MoviesDao

    private val mockMovieItemEntity = MovieItemEntity(id = 1, posterPath = "test", section = 0)
    private val mockMovieDetailEntity = MovieDetailEntity(
        id = 1,
        genres = "test",
        originalLanguage = "test",
        title = "test",
        overview = "test",
        posterPath = "test",
        releaseDate = "test",
        voteAverage = 8,
        videoYoutubeKey = "test",
    )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MoviesDatabase::class.java).build()
        moviesDao = database.moviesDao()
    }

    @Test
    fun writeAndReadMoviesItems() = runBlocking {
        moviesDao.insertMoviesItem(listOf(mockMovieItemEntity))
        val moviesItemsSaved = listOf(mockMovieItemEntity)
        val moviesItems = moviesDao.getMoviesBySection(0)
        assertEquals(moviesItemsSaved, moviesItems)
    }

    @Test
    fun readEmptyMoviesItems() = runBlocking {
        val moviesItems = moviesDao.getMoviesBySection(0)
        assertEquals(moviesItems, listOf())
    }

    @Test
    fun writeAndReadMovieDetail() = runBlocking {
        moviesDao.insertMovieDetail(mockMovieDetailEntity)
        val movieDetailSaved = moviesDao.getMovieDetailById(1)
        assertEquals(movieDetailSaved, mockMovieDetailEntity)
    }

    @Test
    fun readEmptyMovieDetail() = runBlocking {
        val movieDetailSaved = moviesDao.getMovieDetailById(1)
        assertEquals(movieDetailSaved, null)
    }

}