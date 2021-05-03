package com.sayler666.movies.di

import com.sayler666.movies.repository.IMoviesRepository
import com.sayler666.movies.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideMovieRepository(repository: MoviesRepository): IMoviesRepository
}
