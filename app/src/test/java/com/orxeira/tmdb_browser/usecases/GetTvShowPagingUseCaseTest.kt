package com.orxeira.tmdb_browser.usecases

import com.orxeira.tmdb_browser.data.repositories.TvShowRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetTvShowPagingUseCaseTest {
    @Test
    fun `Invoke calls tv show repository`(): Unit = runBlocking {
        val tvShowRepository = mock<TvShowRepository>()

        val sut = GetTvShowPagingUseCase(tvShowRepository)
        sut()

        Mockito.verify(tvShowRepository).getTvShowPaging()
    }
}