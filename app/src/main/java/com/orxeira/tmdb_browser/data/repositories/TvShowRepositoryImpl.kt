package com.orxeira.tmdb_browser.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.orxeira.tmdb_browser.common.addOriginal
import com.orxeira.tmdb_browser.data.database.TvShowLocalDatasource
import com.orxeira.tmdb_browser.data.paging.TvShowPagingSource
import com.orxeira.tmdb_browser.data.server.TvShowRemoteDataSource
import com.orxeira.tmdb_browser.data.server.toDomainModel
import com.orxeira.tmdb_browser.domain.TvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Main repository that handles where to obtain the data from.
 *
 * It receives a local and a remote data source.
 */
class TvShowRepository(
    private val remoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: TvShowLocalDatasource
) {
    /**
     * Gets a Paging Data as a Flow object to use in a PagingDataAdapter in the list view.
     */
    fun getTvShowPaging(): Flow<PagingData<TvShow>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TvShowPagingSource(remoteDataSource, localDataSource) }
    ).flow

    /**
     * Given a tvShow, it gets a list of similar shows.
     */
    suspend fun getSimilarShows(tvShow: TvShow): Flow<List<TvShow>> = flow {
        val results = remoteDataSource.getSimilarShows(tvShow.id).addOriginal(tvShow)
        emit(results)
    }
}

