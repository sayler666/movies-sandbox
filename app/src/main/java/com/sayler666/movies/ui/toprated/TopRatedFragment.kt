package com.sayler666.movies.ui.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sayler666.movies.R
import com.sayler666.movies.databinding.TopRatedFragmentBinding
import com.sayler666.movies.repository.TopRatedResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : Fragment() {

    private var _binding: TopRatedFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TopRatedViewModel by hiltNavGraphViewModels(R.id.navigation)
    private val adapter: MovieAdapter by lazy {
        MovieAdapter { movieId ->
            findNavController().navigate(TopRatedFragmentDirections.actionNavigationTopRatedToNavigationDetails(movieId))
        }
    }

    private val topRatedObserver = Observer<TopRatedResponse> {
        when (it) {
            is TopRatedResponse.Data -> {
                binding.progress.isVisible = false
                adapter.submitList(it.movieDbs)
            }
            is TopRatedResponse.Error -> binding.progress.isVisible = false
            TopRatedResponse.Loading -> binding.progress.isVisible = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TopRatedFragmentBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.movieRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.movieRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() = with(viewModel) {
        topRated.observe(viewLifecycleOwner, topRatedObserver)
    }
}