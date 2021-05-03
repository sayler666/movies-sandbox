package com.sayler666.movies.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import common.MainCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class MoviesServiceTest : ApiAbstract<MoviesService>() {
    @get: Rule
    val coroutinesRule = MainCoroutinesRule()

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MoviesService

    @Before
    fun initService() {
        service = createService(MoviesService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun getNewsFromNetwork() = runBlocking {
        enqueueResponse("/top-rated-response.json")
        val response = (service.fetchTopRated())
        val movies = requireNotNull(response.movies)
        mockWebServer.takeRequest()

        assertThat(movies.count()).isEqualTo(20)
        with(movies[0]) {
            assertThat(id).isEqualTo(19404)
            assertThat(overview).isEqualTo("Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.")
            assertThat(poster_path).isEqualTo("/2CAL2433ZeIihfX1Hb2139CX0pW.jpg")
            assertThat(title).isEqualTo("Dilwale Dulhania Le Jayenge")
        }
    }
}