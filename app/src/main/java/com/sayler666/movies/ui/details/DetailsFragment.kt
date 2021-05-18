package com.sayler666.movies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.sayler666.movies.R
import com.sayler666.movies.databinding.DetailsFragmentBinding
import com.sayler666.movies.db.MovieDb
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by hiltNavGraphViewModels(R.id.navigation)

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()
    private val movieId: Int by lazy { args.movieId }

    private val toolbar by lazy { (requireActivity() as AppCompatActivity).supportActionBar }

    private val movieObserver = { movie: MovieDb ->
        with(binding) {
            poster.load(movie.imgUrl)
            toolbar?.setTitle(movie.title)
            overview.text = movie.overview
            favouriteButton.setImageDrawable(
                getDrawable(
                    requireContext(),
                    if (movie.favourite) R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_star_border_24
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        setupObservers()
        fetchMovie()
        return binding.root
    }

    private fun setupObservers() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(STARTED) {
                movie.collect { movie ->
                    movie?.let { movieObserver(it) }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favouriteButton.setOnClickListener {
            viewModel.toggleFavourite()
        }
    }

    private fun fetchMovie() {
        viewModel.fetchMovie(movieId)
    }
}