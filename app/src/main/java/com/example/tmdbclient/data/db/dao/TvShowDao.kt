package com.example.tmdbclient.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tmdbclient.data.model.tvshow.TvShow

@Dao
interface TvShowDao {

    @Insert
    suspend fun saveTvShows(tvShows: List<TvShow>)

    @Query("DELETE FROM popular_tvshows")
    suspend fun deleteAllTvShows()

    @Query("SELECT * FROM popular_tvshows")
    suspend fun getTvShows(tvShows: List<TvShow>)

}