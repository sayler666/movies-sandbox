package com.sayler666.movies.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    companion object {
        const val API_KEY = "e929994c110d229c0300d9edce07024f"
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("movie/top_rated")
    suspend fun fetchTopRated(
        @Query("api_key") apiKey: String = API_KEY
    ): Page
}