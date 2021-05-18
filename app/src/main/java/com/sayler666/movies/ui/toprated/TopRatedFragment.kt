package com.sayler666.movies.ui.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sayler666.movies.R
import com.sayler666.movies.databinding.TopRatedFragmentBinding
import com.sayler666.movies.repository.TopRatedResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

typealias TopRatedObserver = (value: TopRatedResponse) -> Unit

@InternalCoroutinesApi
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TopRatedFragmentBinding.inflate(inflater, container, false)
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(STARTED) {
                viewModel.topRated.collect {
                    when (it) {
                        is TopRatedResponse.Data -> {
                            binding.progress.isVisible = false
                            adapter.submitList(it.movieDbs)
                        }
                        is TopRatedResponse.Error -> binding.progress.isVisible = false
                        TopRatedResponse.Loading -> binding.progress.isVisible = true
                    }
                }
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.movieRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.movieRecyclerView.adapter = adapter
    }
}