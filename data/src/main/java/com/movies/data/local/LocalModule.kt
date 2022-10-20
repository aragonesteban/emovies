package com.movies.data.local

import android.app.Application
import androidx.room.Room
import com.movies.data.local.movies.LocalMovies
import com.movies.data.local.movies.LocalMoviesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val NAME_DATABASE = "MoviesDatabase"

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MoviesDatabase {
        return Room.databaseBuilder(
            app,
            MoviesDatabase::class.java,
            NAME_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao {
        return moviesDatabase.moviesDao()
    }

    @Provides
    @Singleton
    fun provideLocalMovies(moviesDao: MoviesDao): LocalMovies {
        return LocalMoviesImpl(moviesDao)
    }

}