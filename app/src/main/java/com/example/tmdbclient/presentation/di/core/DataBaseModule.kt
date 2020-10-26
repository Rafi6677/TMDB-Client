package com.example.tmdbclient.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.example.tmdbclient.data.db.TMDBDatabase
import com.example.tmdbclient.data.db.dao.ArtistDao
import com.example.tmdbclient.data.db.dao.MovieDao
import com.example.tmdbclient.data.db.dao.TvShowDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Context): TMDBDatabase {
        return Room.databaseBuilder(
            context,
            TMDBDatabase::class.java,
            "tmdbclient"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(tmdbDatabase: TMDBDatabase): MovieDao {
        return tmdbDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideArtistDao(tmdbDatabase: TMDBDatabase): ArtistDao {
        return tmdbDatabase.artistDao()
    }

    @Singleton
    @Provides
    fun provideTvShowDao(tmdbDatabase: TMDBDatabase): TvShowDao {
        return tmdbDatabase.tvShowDao()
    }

}