package com.movies.data.remote.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import javax.inject.Singleton

private const val MOVIES_API = "https://api.themoviedb.org/3/movie/"

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun provideRetrofit(): Retrofit = buildRetrofit(MOVIES_API)

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi =
        retrofit.create(MoviesApi::class.java)

}