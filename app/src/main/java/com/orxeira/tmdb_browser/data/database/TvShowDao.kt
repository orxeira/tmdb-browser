package com.orxeira.tmdb_browser.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TvShowDao {

    @Query("SELECT *  FROM tv_shows ORDER BY voteAverage DESC LIMIT :size")
    suspend fun getTvShows(size: Int): List<DbTvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTvShows(tvShows: List<DbTvShow>)
}