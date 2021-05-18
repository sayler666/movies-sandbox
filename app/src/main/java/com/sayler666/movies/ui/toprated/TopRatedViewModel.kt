package com.sayler666.movies.ui.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayler666.movies.repository.IMoviesRepository
import com.sayler666.movies.repository.TopRatedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    moviesRepository: IMoviesRepository
) : ViewModel() {
    val topRated = moviesRepository.fetchTopRated().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TopRatedResponse.Loading
    )
}