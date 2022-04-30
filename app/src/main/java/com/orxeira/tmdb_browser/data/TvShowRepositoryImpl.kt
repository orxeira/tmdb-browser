package com.orxeira.tmdb_browser.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.orxeira.tmdb_browser.data.database.TvShowLocalDatasource
import com.orxeira.tmdb_browser.data.paging.TvShowPagingSource
import com.orxeira.tmdb_browser.data.server.TvShowRemoteDataSource
import com.orxeira.tmdb_browser.domain.TvShow
import kotlinx.coroutines.flow.Flow

class TvShowRepository(
    private val remoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: TvShowLocalDatasource
) {
    fun getTvShowPaging(): Flow<PagingData<TvShow>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TvShowPagingSource(remoteDataSource, localDataSource) }
    ).flow
}