package com.sayler666.movies.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayler666.movies.repository.IMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : ViewModel() {

    private val _movieId = MutableStateFlow<Int?>(null)

    val movie = _movieId.filterNotNull()
        .flatMapLatest { moviesRepository.getMovieById(it) }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = null
        )

    fun fetchMovie(movieId: Int) {
        _movieId.value = movieId
    }

    fun toggleFavourite() {
        viewModelScope.launch {
            movie.value?.let { moviesRepository.toggleFavourite(it) }
        }
    }
}