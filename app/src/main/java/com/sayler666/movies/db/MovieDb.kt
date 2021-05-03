package com.sayler666.movies.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MovieDb(
    @PrimaryKey
    val id: Int,
    val title: String,
    val imgUrl: String,
    val overview: String,
    val favourite: Boolean = false,
)
