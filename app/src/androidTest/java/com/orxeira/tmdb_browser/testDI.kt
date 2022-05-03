package com.orxeira.tmdb_browser

import androidx.room.Room
import com.orxeira.tmdb_browser.data.database.TvShowDatabase
import com.orxeira.tmdb_browser.data.database.TvShowLocalDatasource
import com.orxeira.tmdb_browser.data.database.TvShowRoomDatasource
import com.orxeira.tmdb_browser.data.repositories.TvShowRepository
import com.orxeira.tmdb_browser.data.server.TvShowRemoteDataSource
import com.orxeira.tmdb_browser.data.server.TvShowServerDataSource
import com.orxeira.tmdb_browser.domain.TvShow
import com.orxeira.tmdb_browser.ui.detail.DetailListViewModel
import com.orxeira.tmdb_browser.ui.detail.item.DetailViewModel
import com.orxeira.tmdb_browser.ui.main.MainViewModel
import com.orxeira.tmdb_browser.usecases.GetSimilarShowsUseCase
import com.orxeira.tmdb_browser.usecases.GetTvShowPagingUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initTestDI(){
    startKoin { mockedAppModule }
}

val mockedAppModule = module{
    single(named("api_key")) {"1234"}
    single {
        Room.inMemoryDatabaseBuilder(
            get(),
            TvShowDatabase::class.java
        ).build()
    }

    single { get<TvShowDatabase>().tvShowDao() }

    factory<TvShowLocalDatasource> { TvShowRoomDatasource(get()) }
    factory<TvShowRemoteDataSource> { TvShowServerDataSource(get(named("api_key"))) }

    factory { TvShowRepository(get(), get()) }

    factory { GetTvShowPagingUseCase(get()) }
    factory { GetSimilarShowsUseCase(get()) }

    viewModel { MainViewModel(get()) }
    viewModel { (tvShow: TvShow) -> DetailListViewModel(get(), tvShow) }
    viewModel { (tvShow: TvShow) -> DetailViewModel(tvShow) }
}
