package com.orxeira.tmdb_browser.ui

import app.cash.turbine.test
import com.orxeira.tmdb_browser.common.CoroutinesTestRule
import com.orxeira.tmdb_browser.common.FakeLocalDataSource
import com.orxeira.tmdb_browser.common.FakeRemoteDataSource
import com.orxeira.tmdb_browser.common.*
import com.orxeira.tmdb_browser.data.repositories.TvShowRepository
import com.orxeira.tmdb_browser.common.fakeTvShows
import com.orxeira.tmdb_browser.usecases.GetSimilarShowsUseCase
import com.orxeira.tmdb_browser.ui.detail.DetailListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class DetailListIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `List of similar tvShows is returned with request show as first on the list`() = runTest {
        val expectedResult = fakeTvShows.addOriginal(fakeTvShows[3])

        val remoteDataSource = FakeRemoteDataSource()
        val localDataSource = FakeLocalDataSource()
        val tvShowRepository = TvShowRepository(remoteDataSource, localDataSource)
        val getSimilarShowsUseCase = GetSimilarShowsUseCase(tvShowRepository)

        val viewModel = DetailListViewModel(getSimilarShowsUseCase, fakeTvShows[3])

        viewModel.tvShows.test {
            //The list is initially empty before it get's assigned a value
            assertEquals(listOf(), awaitItem())
            assertEquals(expectedResult, awaitItem())
        }
    }
}