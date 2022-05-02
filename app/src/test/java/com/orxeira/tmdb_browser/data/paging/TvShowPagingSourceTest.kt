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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.junit.JUnitAsserter.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TvShowPagingSourceTest {
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


    @Test
    fun `PagingSource paginates correctly on first iteration`() = runTest {
        val testList = listOf(fakeTvShows[0])
        whenever(remoteDataSource.getTopRatedTvShows(1)).thenReturn(testList)

        val pagingSource = TvShowPagingSource(remoteDataSource, localDataSource)
        assertEquals(
            "",
            expected = PagingSource.LoadResult.Page(
                data = listOf(fakeTvShows[0]),
                prevKey = null,
                nextKey = 2
            ),
            actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 20,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `PagingSource paginates correctly on non initial iteration`() = runTest {
        val testList = listOf(fakeTvShows[4])
        whenever(remoteDataSource.getTopRatedTvShows(4)).thenReturn(testList)

        val pagingSource = TvShowPagingSource(remoteDataSource, localDataSource)
        assertEquals(
            "",
            expected = PagingSource.LoadResult.Page(
                data = listOf(fakeTvShows[4]),
                prevKey = 3,
                nextKey = 5
            ),
            actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 4,
                    loadSize = 20,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `PagingSource uses remote datasource to obtain data`() = runTest {
        val testList = listOf(fakeTvShows[0])
        whenever(remoteDataSource.getTopRatedTvShows(2)).thenReturn(testList)

        val pagingSource = TvShowPagingSource(remoteDataSource, localDataSource)

        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 2,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        verify(remoteDataSource).getTopRatedTvShows(2)
    }

    @Test
    fun `PagingSource uses localDataSource on remoteDatasource error`() = runTest {
        val testList = listOf(fakeTvShows[1])
        whenever(localDataSource.getTvShows(20)).thenReturn(testList)

        val pagingSource = TvShowPagingSource(remoteDataSource, localDataSource)
        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )


        verify(localDataSource).getTvShows(20)
    }
}