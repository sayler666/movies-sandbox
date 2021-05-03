package com.sayler666.movies.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Page(
    @Json(name = "results") val movies: List<MovieApi>
)

@JsonClass(generateAdapter = true)
data class MovieApi(
    val title: String,
    val poster_path: String,
    val overview: String,
    val id: Int
)