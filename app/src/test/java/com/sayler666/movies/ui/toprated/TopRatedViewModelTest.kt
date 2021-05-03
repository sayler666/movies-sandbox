package com.sayler666.movies.ui.toprated


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.sayler666.movies.repository.MoviesRepository
import com.sayler666.movies.repository.TopRatedResponse
import common.MainCoroutinesRule
import common.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    fun fetchTopRatedLoadingState() {
        every { repository.fetchTopRated() } returns flow { emit(TopRatedResponse.Loading) }
        viewModel = TopRatedViewModel(repository)
        val value = viewModel.topRated.getOrAwaitValue()
        Truth.assertThat(value).isEqualTo(TopRatedResponse.Loading)
    }

    @Test
    fun fetchTopRatedDataState() {
        every { repository.fetchTopRated() } returns flow { emit(TopRatedResponse.Data(emptyList())) }
        viewModel = TopRatedViewModel(repository)
        val value = viewModel.topRated.getOrAwaitValue()
        Truth.assertThat(value).isEqualTo(TopRatedResponse.Data(emptyList()))
    }

    @Test
    fun fetchTopRatedErrorState() {
        every { repository.fetchTopRated() } returns flow { emit(TopRatedResponse.Error(Throwable())) }
        viewModel = TopRatedViewModel(repository)
        val value = viewModel.topRated.getOrAwaitValue()
        Truth.assertThat(value).isInstanceOf(TopRatedResponse.Error::class.java)
    }
}