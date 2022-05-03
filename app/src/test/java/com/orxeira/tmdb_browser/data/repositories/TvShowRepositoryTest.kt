package com.orxeira.tmdb_browser.data.repositories

import com.orxeira.tmdb_browser.TvShowFactory
import com.orxeira.tmdb_browser.common.addOriginal
import com.orxeira.tmdb_browser.data.database.TvShowLocalDatasource
import com.orxeira.tmdb_browser.data.server.TvShowRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class TvShowRepositoryTest {
    private val postFactory = TvShowFactory()
    private val fakeTvShows = listOf(
        postFactory.createTvShow(),
        postFactory.createTvShow(),
        postFactory.createTvShow(),
        postFactory.createTvShow(),
        postFactory.createTvShow()
    )

    @Mock
    lateinit var remoteDataSource: TvShowRemoteDataSource

    @Mock
    lateinit var localDataSource: TvShowLocalDatasource

    private lateinit var sut: TvShowRepository

    @Before
    fun setup(){
        sut = TvShowRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `When requesting similar tv shows the remoteDataSource is called`():Unit = runBlocking {
        whenever(remoteDataSource.getSimilarShows(fakeTvShows[4].id)).thenReturn(fakeTvShows)

        sut.getSimilarShows(fakeTvShows[4])

        verify(remoteDataSource).getSimilarShows(fakeTvShows[4].id)
    }

    @Test
    fun `getSimilarShows adds original at index 0`():Unit = runBlocking {
        //Define expected value(A list of fakeTvShows with item fakeTvShow[4] at the beginning
        val tvShows = flowOf(fakeTvShows.addOriginal(fakeTvShows[4]))

        //When we call remoteDataSource it will give us the unsorted list
        whenever(remoteDataSource.getSimilarShows(fakeTvShows[4].id)).thenReturn(fakeTvShows)


        //Execute the call with fakeTvShow[4]
        val result = sut.getSimilarShows(fakeTvShows[4])

        //The result and expected values should be the same.
        assertEquals(tvShows.last(), result.last())
    }


}