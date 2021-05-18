package com.sayler666.movies.ui.toprated


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sayler666.movies.repository.MoviesRepository
import com.sayler666.movies.repository.TopRatedResponse
import common.MainCoroutinesRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class ArticleListViewModelTest {
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var repository: MoviesRepository
    private lateinit var viewModel: TopRatedViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun fetchTopRatedLoadingState() = runBlocking {
        every { repository.fetchTopRated() } returns flow { emit(TopRatedResponse.Loading) }
        viewModel = TopRatedViewModel(repository)
        viewModel.topRated.test {
            assertThat(expectItem()).isEqualTo(TopRatedResponse.Loading)
        }
    }

    @Test
    fun fetchTopRatedDataState() = runBlocking {
        every { repository.fetchTopRated() } returns flow { emit(TopRatedResponse.Data(emptyList())) }
        viewModel = TopRatedViewModel(repository)
        viewModel.topRated.test {
            assertThat(expectItem()).isEqualTo(TopRatedResponse.Data(emptyList()))
        }
    }

    @Test
    fun fetchTopRatedErrorState() = runBlocking {
        every { repository.fetchTopRated() } returns flow { emit(TopRatedResponse.Error(Throwable())) }
        viewModel = TopRatedViewModel(repository)
        viewModel.topRated.test {
            assertThat(expectItem()).isInstanceOf(TopRatedResponse.Error::class.java)
        }
    }
}