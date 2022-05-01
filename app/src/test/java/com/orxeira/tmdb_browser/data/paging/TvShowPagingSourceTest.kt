package com.orxeira.tmdb_browser.data.paging

import androidx.paging.PagingSource
import com.orxeira.tmdb_browser.TvShowFactory
import com.orxeira.tmdb_browser.data.database.TvShowLocalDatasource
import com.orxeira.tmdb_browser.data.server.TvShowRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import kotlin.test.junit.JUnitAsserter.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TvShowPagingSourceTest{
    private val postFactory = TvShowFactory()
    private val fakeTvShows = listOf(
        postFactory.createTvShow(),
        postFactory.createTvShow(),
        postFactory.createTvShow()
    )

    @Mock lateinit var remoteDataSource: TvShowRemoteDataSource

    @Mock lateinit var localDataSource: TvShowLocalDatasource

    @Test
    fun itemKeyedSubredditPagingSource() = runTest {

        val testList = listOf(fakeTvShows[0], fakeTvShows[1])
        whenever(remoteDataSource.getTopRatedTvShows(0)).thenReturn(testList)
        whenever(localDataSource.getTvShows(2)).thenReturn(testList)

        val pagingSource = TvShowPagingSource(remoteDataSource,localDataSource)
        assertEquals("",
            expected = PagingSource.LoadResult.Page(
                data = listOf(fakeTvShows[0], fakeTvShows[1]),
                prevKey = null,
                nextKey = fakeTvShows[0].id
            ),
            actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }
}