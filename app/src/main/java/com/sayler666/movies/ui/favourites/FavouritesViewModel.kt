package com.sayler666.movies.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sayler666.movies.repository.IMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    moviesRepository: IMoviesRepository
) : ViewModel() {
    val favourites = moviesRepository.getFavourites().asLiveData()
}