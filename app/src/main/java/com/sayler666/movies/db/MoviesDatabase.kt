package com.sayler666.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieDb::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}