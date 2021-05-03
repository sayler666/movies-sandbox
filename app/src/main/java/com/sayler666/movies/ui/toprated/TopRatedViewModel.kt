package com.sayler666.movies.ui.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sayler666.movies.repository.IMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    moviesRepository: IMoviesRepository
) : ViewModel() {
    val topRated = moviesRepository.fetchTopRated().asLiveData()
}