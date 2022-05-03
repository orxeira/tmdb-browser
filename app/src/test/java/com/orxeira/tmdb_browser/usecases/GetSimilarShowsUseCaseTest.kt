package com.orxeira.tmdb_browser.usecases

import com.orxeira.tmdb_browser.common.TvShowFactory
import com.orxeira.tmdb_browser.data.repositories.TvShowRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock

class GetSimilarShowsUseCaseTest {

    @Test
    fun `Invoke calls tvShow repository`(): Unit = runBlocking {
        val fakeTvShow = TvShowFactory().createTvShow()
        val tvShowRepository = mock<TvShowRepository>()

        val sut = GetSimilarShowsUseCase(tvShowRepository)
        sut(fakeTvShow)

        verify(tvShowRepository).getSimilarShows(fakeTvShow)
    }
}