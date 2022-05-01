package com.orxeira.tmdb_browser.data

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

    suspend fun getSimilarShows(tvShow: TvShow): Flow<List<TvShow>> = flow {
        val results = remoteDataSource.getSimilarShows(tvShow.id).body()!!.results.toDomainModel().addOriginal(tvShow)
        emit(results)
    }.flowOn(Dispatchers.IO)
}

