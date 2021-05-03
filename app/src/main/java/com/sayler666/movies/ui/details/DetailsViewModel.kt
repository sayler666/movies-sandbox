package com.sayler666.movies.ui.details

import androidx.lifecycle.*
import com.sayler666.movies.repository.IMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesRepository: IMoviesRepository
) : ViewModel() {
    private val _movieId = MutableLiveData<Int>()

    val movie = _movieId.switchMap {
        moviesRepository.getMovieById(it).asLiveData()
    }

    fun fetchMovie(movieId: Int) {
        _movieId.value = movieId
    }

    fun toggleFavourite() {
        viewModelScope.launch {
            movie.value?.let { moviesRepository.toggleFavourite(it) }
        }
    }
}