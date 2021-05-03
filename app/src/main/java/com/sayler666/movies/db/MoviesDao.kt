package com.sayler666.movies.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieDbs: List<MovieDb>)

    @Query("SELECT * FROM movies_table")
    fun getMoviesFlow(): Flow<List<MovieDb>>

    @Query("SELECT * FROM movies_table")
    suspend fun getMovies(): List<MovieDb>

    @Query("SELECT * FROM movies_table WHERE favourite = 1")
    fun getFavouritesFlow(): Flow<List<MovieDb>>

    @Query("SELECT * FROM movies_table WHERE favourite = 1")
    suspend fun getFavourites(): List<MovieDb>

    @Query("SELECT * FROM movies_table WHERE id = :id")
    fun getMovieByIdFlow(id: Int): Flow<MovieDb>

    @Update
    suspend fun updateMovie(movieDb: MovieDb)
}