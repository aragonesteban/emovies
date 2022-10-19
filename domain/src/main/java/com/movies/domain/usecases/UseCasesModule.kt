package com.movies.domain.usecases

import com.movies.domain.usecases.movies.MoviesUseCase
import com.movies.domain.usecases.movies.MoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCasesModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsMoviesUseCase(
        moviesUseCaseImpl: MoviesUseCaseImpl
    ): MoviesUseCase

}