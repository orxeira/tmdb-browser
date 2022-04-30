package com.orxeira.tmdb_browser.ui.detail.item

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.orxeira.tmdb_browser.R
import com.orxeira.tmdb_browser.databinding.FragmentDetailBinding
import com.orxeira.tmdb_browser.domain.TvShow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var args: Bundle

    private val viewModel: DetailViewModel by viewModel {
        parametersOf(
            args.getParcelable<TvShow>(ARG_TV_SHOW)
        )
    }

    companion object {

        const val ARG_TV_SHOW = "arg_tv_show"

        fun newInstance(tvShow: TvShow): DetailFragment{
            return DetailFragment().apply {
                arguments = bundleOf(ARG_TV_SHOW to tvShow)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = requireArguments()

        _binding = FragmentDetailBinding.bind(view)

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

                @SuppressLint("RestrictedApi")
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

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.tvShows.collectLatest {
                    binding.tvShow = it[0]
                }
            }
        }
    }
}