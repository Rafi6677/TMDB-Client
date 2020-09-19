package com.example.tmdbclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdbclient.data.db.dao.ArtistDao
import com.example.tmdbclient.data.db.dao.MovieDao
import com.example.tmdbclient.data.db.dao.TvShowDao
import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.data.model.movie.Movie
import com.example.tmdbclient.data.model.tvshow.TvShow

@Database(entities = [Artist::class, Movie::class, TvShow::class],
version = 1,
exportSchema = false
)
abstract class TMDBDatabase: RoomDatabase() {

    abstract fun artistDao(): ArtistDao
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

}