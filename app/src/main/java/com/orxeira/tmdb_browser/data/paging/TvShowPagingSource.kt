package com.orxeira.tmdb_browser.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.orxeira.tmdb_browser.data.database.TvShowLocalDatasource
import com.orxeira.tmdb_browser.data.database.toDomain
import com.orxeira.tmdb_browser.data.database.toRoom
import com.orxeira.tmdb_browser.data.server.TvShowRemoteDataSource
import com.orxeira.tmdb_browser.data.server.toDomainModel
import com.orxeira.tmdb_browser.domain.TvShow
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

/**
 * The main paging source for the TvShow list. This source acts as a repository as it handles where
 * to get it's data from. In this case we have opted for getting all of our data from our remote
 * datasource and only relying in our local data source in case of error.
 *
 */
class TvShowPagingSource constructor(
    private val service: TvShowRemoteDataSource,
    private val localDataSource: TvShowLocalDatasource
) : PagingSource<Int, TvShow>() {

    companion object {
        const val INITIAL_PAGE_INDEX = 0
        const val INITIAL_PAGE_INDEX_REMOTE = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val pageNumber = params.key ?: INITIAL_PAGE_INDEX_REMOTE
        return try {
            val response = service.getTopRatedTvShows(pageNumber)
            delay(1000L)
            val pagedResponse = response.body()
            val data = pagedResponse?.results?.map { it.toDomainModel() }.also { itr ->
                if (itr != null) {
                    itr.map { it.toRoom() }
                    localDataSource.addTvShows(itr.map { it.toRoom() })
                }
            }
            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = pageNumber + 1
            )
        } catch (e: IOException) {
            getLocalData(params, e)
        } catch (e: HttpException) {
            getLocalData(params, e)
        } catch (e: Exception) {
            getLocalData(params, e)
        }
    }

    //In case of error, we load whatever we have stored.
    private suspend fun getLocalData(
        params: LoadParams<Int>,
        e: Exception
    ): LoadResult<Int, TvShow> {
        val pageNumberLocal = params.key ?: INITIAL_PAGE_INDEX
        val randomTvShow = localDataSource.getTvShows(params.loadSize)
        val result = randomTvShow.map { it.toDomain() }
        return if (result.isNotEmpty()) {
            LoadResult.Page(
                data = result,
                prevKey = if (pageNumberLocal == INITIAL_PAGE_INDEX) null else pageNumberLocal - 1,
                nextKey = if (result.isNullOrEmpty()) null else pageNumberLocal + 1
            )
        } else {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}