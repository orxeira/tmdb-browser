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


class DetailListFragment : Fragment(R.layout.fragment_detail_list) {

    private val safeArgs: DetailListFragmentArgs by navArgs()

    private val viewModel: DetailListViewModel by viewModel { parametersOf(safeArgs.tvShow) }

    private var _binding: FragmentDetailListBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailListBinding.bind(view)

        binding.pager.setPageTransformer(DepthPageTransformer())

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