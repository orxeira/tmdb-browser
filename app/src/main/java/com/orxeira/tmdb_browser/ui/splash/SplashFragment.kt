package com.orxeira.tmdb_browser.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.orxeira.tmdb_browser.R
import com.orxeira.tmdb_browser.common.getNavController
import com.orxeira.tmdb_browser.common.popToNewDestinationBase
import kotlinx.coroutines.delay


class SplashFragment : Fragment() {

    private val navController by lazy {
        getNavController(R.id.nv_host_fragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            delay(2000L)
            findNavController().popToNewDestinationBase(R.id.go_to_list)
        }
    }
}