package com.orxeira.tmdb_browser.ui.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.orxeira.tmdb_browser.R
import com.orxeira.tmdb_browser.common.withLoadStateAdapters
import com.orxeira.tmdb_browser.databinding.FragmentMainBinding
import com.orxeira.tmdb_browser.domain.TvShow
import com.orxeira.tmdb_browser.ui.main.adapter.LoadItemAdapter
import com.orxeira.tmdb_browser.ui.main.adapter.PagingTvShowAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A Fragment that is reponsible for showing a list of TvShows with pagination.
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    /**
     * The paging data adapter for the recyclerView.
     */
    private val adapter = PagingTvShowAdapter(::onTvShowClicked)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupOservers()
    }

    /**
     * A LoadStateAdapter is added to the recyclerView to handle loading states.
     */
    private fun setupView() = with(binding) {
        recycler.adapter = adapter.withLoadStateAdapters(
            LoadItemAdapter(retry = adapter::retry),
            LoadItemAdapter(retry = adapter::retry)
        )
    }

    /**
     * This method initialises the collect function for out tvShow Flow and tells the ViewModel to
     * load the data for our recycler view.
     */
    private fun setupOservers() {
        viewModel.getPopulartvShows()

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.tvShows.collect {
                    loadRecyclerData(it)
                }
            }
        }
    }

    private suspend fun loadRecyclerData(data: PagingData<TvShow>) {
        binding.apply {
            adapter.submitData(data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onTvShowClicked(tvShow: TvShow) {
        val action = MainFragmentDirections.listToDetail(tvShow)
        findNavController().navigate(action)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}