package com.example.tmdbclient.data.repository.tvshow

import android.util.Log
import com.example.tmdbclient.data.model.tvshow.TvShow
import com.example.tmdbclient.data.repository.tvshow.datasource.TvShowCacheDataSource
import com.example.tmdbclient.data.repository.tvshow.datasource.TvShowLocalDataSource
import com.example.tmdbclient.data.repository.tvshow.datasource.TvShowRemoteDataSource
import com.example.tmdbclient.domain.repository.TvShowRepository
import java.lang.Exception

class TvShowRepositoryImpl(
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowCacheDataSource: TvShowCacheDataSource
) : TvShowRepository {

    override suspend fun getTvShows(): List<TvShow>? {
        return getTvShowsFromCache()
    }

    override suspend fun updateTvShows(): List<TvShow>? {
        val newListOfTvShows = getTvShowsFromAPI()

        tvShowLocalDataSource.clear()
        tvShowLocalDataSource.saveTvShowsToDB(newListOfTvShows)
        tvShowCacheDataSource.saveTvShowsToCache(newListOfTvShows)

        return newListOfTvShows
    }

    suspend fun getTvShowsFromAPI(): List<TvShow> {
        lateinit var tvShowLists: List<TvShow>

        try {
            val response = tvShowRemoteDataSource.getTvShows()
            val body = response.body()

            if (body != null) {
                tvShowLists = body.tvShows
            }
        } catch (e: Exception) {
            Log.i("MyTag", e.message.toString())
        }

        return tvShowLists
    }

    suspend fun getTvShowsDromDB(): List<TvShow> {
        lateinit var tvShowList: List<TvShow>

        try {
            tvShowList = tvShowLocalDataSource.getTvShowsFromDB()
        } catch (e: Exception) {
            Log.i("MyTag", e.message.toString())
        }

        if (tvShowList.isNotEmpty()) {
            return tvShowList
        } else {
            tvShowList = getTvShowsFromAPI()
            tvShowLocalDataSource.saveTvShowsToDB(tvShowList)
        }

        return tvShowList
    }

    suspend fun getTvShowsFromCache(): List<TvShow> {
        lateinit var tvShowList: List<TvShow>

        try {
            tvShowList = tvShowCacheDataSource.getTvShowsFromCache()
        } catch (e: Exception) {
            Log.i("MyTag", e.message.toString())
        }

        if (tvShowList.isNotEmpty()) {
            return tvShowList
        } else {
            tvShowList = getTvShowsDromDB()
            tvShowCacheDataSource.saveTvShowsToCache(tvShowList)
        }

        return tvShowList
    }

}