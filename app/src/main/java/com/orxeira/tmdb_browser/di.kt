package com.orxeira.tmdb_browser

import android.app.Application
import androidx.room.Room
import com.orxeira.tmdb_browser.data.PagingRepository
import com.orxeira.tmdb_browser.data.TvShowPaging
import com.orxeira.tmdb_browser.data.TvShowRepository
import com.orxeira.tmdb_browser.data.database.TvShowDatabase
import com.orxeira.tmdb_browser.data.database.TvShowLocalDatasource
import com.orxeira.tmdb_browser.data.database.TvShowRoomDatasource
import com.orxeira.tmdb_browser.data.server.TvShowRemoteDataSource
import com.orxeira.tmdb_browser.data.server.TvShowServerDataSource
import com.orxeira.tmdb_browser.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDi() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDi)
        modules(appModule)
    }
}

private val appModule = module {
    single(named("api_key")) { androidApplication().getString(R.string.api_key) }
    single {
        Room.databaseBuilder(
            get(),
            TvShowDatabase::class.java, "tvShow-db"
        ).build()
    }
    single { get<TvShowDatabase>().tvShowDao() }

    factory<TvShowLocalDatasource> { TvShowRoomDatasource(get()) }
    factory<TvShowRemoteDataSource> { TvShowServerDataSource(get(named("api_key"))) }
    factory { TvShowRepository(get(), get()) }
    factory { TvShowPaging(get()) }
    factory { PagingRepository(get()) }

    viewModel { MainViewModel(get()) }
}