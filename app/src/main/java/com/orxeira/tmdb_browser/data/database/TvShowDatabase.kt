package com.orxeira.tmdb_browser.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DbTvShow::class],
    version = 1,
    exportSchema = false
)
abstract class TvShowDatabase: RoomDatabase() {
    abstract fun tvShowDao(): TvShowDao
}