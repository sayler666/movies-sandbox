package com.sayler666.movies.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sayler666.movies.R
import com.sayler666.movies.databinding.FavouritesFragmentBinding
import com.sayler666.movies.db.MovieDb
import com.sayler666.movies.ui.toprated.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by hiltNavGraphViewModels(R.id.navigation)

    private var _binding: FavouritesFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter: MovieAdapter by lazy {
        MovieAdapter { movieId ->
            findNavController().navigate(FavouritesFragmentDirections.actionNavigationFavouritesToNavigationDetails(movieId))
        }
    }

    private val favouritesObserver = Observer<List<MovieDb>> { movies ->
        adapter.submitList(movies)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavouritesFragmentBinding.inflate(inflater, container, false)
        binding.movieRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.movieRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() = with(viewModel) {
        favourites.observe(viewLifecycleOwner, favouritesObserver)
    }
}