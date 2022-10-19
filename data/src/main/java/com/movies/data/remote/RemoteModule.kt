package com.movies.data.remote

import com.movies.data.remote.movies.RemoteMovies
import com.movies.data.remote.movies.RemoteMoviesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun bindRemoteMovies(
        remoteProductsImpl: RemoteMoviesImpl
    ): RemoteMovies

}