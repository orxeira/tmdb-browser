package com.orxeira.tmdb_browser.ui

import app.cash.turbine.test
import com.orxeira.tmdb_browser.common.CoroutinesTestRule
import com.orxeira.tmdb_browser.common.fakeTvShows
import com.orxeira.tmdb_browser.ui.detail.item.DetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class DetailViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `Parameter TvShow is assigned to tvShow state flow`() = runTest {
        val expectedResult = fakeTvShows[3]



        val viewModel = DetailViewModel(fakeTvShows[3])

        viewModel.tvShows.test {
            //The list is initially empty before it get's assigned a value
            assertEquals(null, awaitItem())
            assertEquals(expectedResult, awaitItem())
        }
    }
}