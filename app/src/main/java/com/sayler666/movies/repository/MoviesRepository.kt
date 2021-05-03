package com.sayler666.movies.repository

import com.sayler666.movies.api.MovieApi
import com.sayler666.movies.api.MoviesService
import com.sayler666.movies.db.MovieDb
import com.sayler666.movies.db.MoviesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import javax.inject.Inject

sealed class TopRatedResponse {
    object Loading : TopRatedResponse()
    data class Error(val throwable: Throwable) : TopRatedResponse()
    data class Data(val movieDbs: List<MovieDb>) : TopRatedResponse()
}

interface IMoviesRepository {
    fun fetchTopRated(): Flow<TopRatedResponse>
    fun getMovieById(movieId: Int): Flow<MovieDb>
    fun getFavourites(): Flow<List<MovieDb>>
    suspend fun toggleFavourite(movie: MovieDb)
}

class MoviesRepository @Inject constructor(
    private val service: MoviesService,
    private val moviesDao: MoviesDao
) : IMoviesRepository {
    override fun fetchTopRated(): Flow<TopRatedResponse> = flow {
        emit(TopRatedResponse.Loading)
        try {
            moviesDao.getMovies().let { if (it.isNotEmpty()) emit(TopRatedResponse.Data(it)) }
            service.fetchTopRated().movies
                .map { it.toModel() }
                .preserveFavourites()
                .saveToLocalDB()
        } catch (e: HttpException) {
            emit(TopRatedResponse.Error(e))
        } catch (e: Exception) {
            emit(TopRatedResponse.Error(e))
        }
        emitAll(moviesDao.getMoviesFlow().map { TopRatedResponse.Data(it) })
    }.flowOn(Dispatchers.IO)

    private suspend fun Iterable<MovieDb>.preserveFavourites(): List<MovieDb> {
        val favourites = moviesDao.getFavourites()
        return map { movieRemote ->
            if (favourites.any { it.id == movieRemote.id }) movieRemote.copy(favourite = true) else movieRemote
        }
    }

    private suspend fun List<MovieDb>.saveToLocalDB() = also { moviesDao.insertAll(this) }

    override fun getMovieById(movieId: Int): Flow<MovieDb> = moviesDao.getMovieByIdFlow(movieId)

    override fun getFavourites(): Flow<List<MovieDb>> = moviesDao.getFavouritesFlow()

    override suspend fun toggleFavourite(movie: MovieDb) = moviesDao.updateMovie(movie.copy(favourite = !movie.favourite))

}

fun MovieApi.toModel() = MovieDb(
    id = id,
    title = title,
    imgUrl = IMAGE_BASE_URL + poster_path,
    overview = overview
)

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"