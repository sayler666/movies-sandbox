package com.sayler666.movies.di

import android.app.Application
import androidx.room.Room
import com.sayler666.movies.db.MoviesDao
import com.sayler666.movies.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MoviesDatabase = Room
        .databaseBuilder(app, MoviesDatabase::class.java, "movies_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideArticleDao(database: MoviesDatabase): MoviesDao = database.movieDao()
}