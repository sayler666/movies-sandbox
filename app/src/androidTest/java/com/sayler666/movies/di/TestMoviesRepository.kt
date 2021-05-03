package com.sayler666.movies.di

import android.util.Log
import com.sayler666.movies.db.MovieDb
import com.sayler666.movies.repository.IMoviesRepository
import com.sayler666.movies.repository.TopRatedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TestMoviesRepository @Inject constructor() : IMoviesRepository {
    override fun fetchTopRated(): Flow<TopRatedResponse> = flow {
        emit(TopRatedResponse.Data(movies))
    }.flowOn(Dispatchers.IO)

    override fun getMovieById(movieId: Int): Flow<MovieDb> = flow {
        emit(movies.first { it.id == movieId })
    }

    override fun getFavourites(): Flow<List<MovieDb>> = flow { emit(movies) }

    override suspend fun toggleFavourite(movie: MovieDb) {}

    companion object {
        val movies = listOf(
            MovieDb(1, "Title1", "url1", "overview1", favourite = false),
            MovieDb(2, "Title2", "url2", "overview2", favourite = false),
            MovieDb(3, "Title3", "url3", "overview3", favourite = false)
        )
    }
}