package com.orxeira.tmdb_browser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.orxeira.tmdb_browser.R
import com.orxeira.tmdb_browser.databinding.FragmentDetailListBinding
import com.orxeira.tmdb_browser.ui.detail.item.DetailFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * This fragment loads a viewPager2 with the selected TvShow and other similar TvShows.
 * There is no paging in this view yet.
 */
class DetailListFragment : Fragment(R.layout.fragment_detail_list) {

    private val safeArgs: DetailListFragmentArgs by navArgs()

    /**
     * We get the viewModel with Koin's by viewModel and in this case we are giving it the
     * TvShow argument received form the SafeArgs.
     */
    private val viewModel: DetailListViewModel by viewModel { parametersOf(safeArgs.tvShow) }

    private var _binding: FragmentDetailListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailListBinding.bind(view)

        // This transformer adds a cool animation when swiping left
        binding.pager.setPageTransformer(DepthPageTransformer())

        startObservers()
    }

    /**
     * This method collects the the flow from the ViewModel and creates a simple adapter for the
     * viewPager2. For each item in the received list, it creates a new DetailFragment.
     */
    private fun startObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.tvShows.collectLatest {
                    binding.pager.adapter = object : FragmentStateAdapter(this@DetailListFragment) {
                        override fun createFragment(position: Int): Fragment {
                            return DetailFragment.newInstance(it[position])
                        }

                        override fun getItemCount(): Int {
                            return it.size
                        }
                    }
                }
            }
        }
    }
}