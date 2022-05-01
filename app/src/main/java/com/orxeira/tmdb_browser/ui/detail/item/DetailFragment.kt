package com.orxeira.tmdb_browser.ui.detail.item

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.orxeira.tmdb_browser.R
import com.orxeira.tmdb_browser.common.setupActionBar
import com.orxeira.tmdb_browser.databinding.FragmentDetailBinding
import com.orxeira.tmdb_browser.domain.TvShow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * This view fragment shows the TvShow's information in a MotionLayout.
 * It receives a TvShow as a parameter.
 *
 * Known bug: Poster view shrinks during animation instead of maintaining it's original size
 * as it should.
 */

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    //ViewModel is obtained with Koin's by viewModel function and we're passing the argument TvShow
    private val viewModel: DetailViewModel by viewModel {
        parametersOf(
            requireArguments().getParcelable<TvShow>(ARG_TV_SHOW)
        )
    }

    /**
     * This companion object is used to instantiate the Fragment with a TvShow argument as we
     * cannot use SafeArgs in this case.
     */
    companion object {
        const val ARG_TV_SHOW = "arg_tv_show"

        fun newInstance(tvShow: TvShow): DetailFragment {
            return DetailFragment().apply {
                arguments = bundleOf(ARG_TV_SHOW to tvShow)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)

        addTransitionHandling()

        setupToolbar()
        setupObserver()

    }

    /**
     * Transition Listener for the MotionLayout handles the poster and toolbar
     * animations.
     */
    private fun addTransitionHandling() {
        with(binding) {
            detailsMotion.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionTrigger(
                    p0: MotionLayout?,
                    p1: Int,
                    p2: Boolean,
                    p3: Float
                ) {
                }

                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

                override fun onTransitionChange(
                    motionLayout: MotionLayout,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    detailsAppbarBackground.cutProgress = 1f - progress
                    detailsPoster.visibility = View.VISIBLE
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                    when (currentId) {
                        R.id.end -> {
                            detailsAppbarBackground.cutProgress = 0f
                            detailsPoster.visibility = View.GONE
                        }
                        R.id.start -> {
                            detailsAppbarBackground.cutProgress = 1f
                            detailsPoster.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }

    /**
     * Adding the layout's toolbar as the actionbar and setting the back button
     */
    private fun setupToolbar() {
        with(activity as AppCompatActivity) {
            setupActionBar(binding.detailsToolbar) {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    /**
     * Collect the viewModel's tvShow and load it into the layout's databinding
     */
    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.tvShows.collectLatest {
                    binding.tvShow = it[0]
                }
            }
        }
    }


    /**
     * Give functionality to the action bar's back button.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
            false
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}