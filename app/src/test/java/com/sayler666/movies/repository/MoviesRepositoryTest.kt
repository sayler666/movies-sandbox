package com.sayler666.movies.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sayler666.movies.api.MoviesService
import com.sayler666.movies.db.MovieDb
import com.sayler666.movies.db.MoviesDao
import common.MainCoroutinesRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesRepositoryTest {
    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var moviesDao: MoviesDao

    @MockK
    private lateinit var service: MoviesService

    private lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = MoviesRepository(service, moviesDao)
    }

    @Test
    fun getMovieByIdFlow() = runBlocking {
        val movie = MovieDb(1, "title", "url", "overview", false)
        every { moviesDao.getMovieByIdFlow(eq(1)) } returns flow { emit(movie) }

        val flow = repository.getMovieById(1)

        flow.collect { result ->
            assertThat(result).isEqualTo(movie)
        }
    }
}