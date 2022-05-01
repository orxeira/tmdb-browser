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
import com.orxeira.tmdb_browser.common.error.Error
import com.orxeira.tmdb_browser.common.error.ErrorEntity
import com.orxeira.tmdb_browser.common.shortToast
import com.orxeira.tmdb_browser.common.withLoadStateAdapters
import com.orxeira.tmdb_browser.databinding.FragmentMainBinding
import com.orxeira.tmdb_browser.domain.TvShow
import com.orxeira.tmdb_browser.ui.TvShowState
import com.orxeira.tmdb_browser.ui.main.adapter.LoadItemAdapter
import com.orxeira.tmdb_browser.ui.main.adapter.PagingTvShowAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

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

    private fun setupView() = with(binding) {
        recycler.adapter = adapter.withLoadStateAdapters(
            LoadItemAdapter(retry = adapter::retry),
            LoadItemAdapter(retry = adapter::retry)
        )
    }

    private fun setupOservers() {
        viewModel.getPopulartvShows()

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.tvShows.collect{
                    loadRecyclerData(it)
                }
            }
            launch {
                viewModel.state.collect(::stateHandler)
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
        viewModel.setTvShow(tvShow)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun stateHandler(state: TvShowState) {
        binding.prBar.visibility = View.GONE
        when (state) {
            is TvShowState.Loading -> {
                binding.prBar.visibility = View.VISIBLE
            }
            is TvShowState.SuccessTvShow -> {
                val action = MainFragmentDirections.goToDetail(state.data)
                viewModel.clearTvShowState()
                findNavController().navigate(action)
            }
            is TvShowState.Error -> {
                errorStateHandler(state.error)
            }
            else -> {
            }
        }
    }

    private fun errorStateHandler(error: Error) =
        when (error) {
            is ErrorEntity -> shortToast(error.message)
            else -> shortToast(getString(R.string.error_connection))
        }

}