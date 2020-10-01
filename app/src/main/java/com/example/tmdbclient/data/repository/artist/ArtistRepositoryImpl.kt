package com.example.tmdbclient.data.repository.artist

import android.util.Log
import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.data.repository.artist.datasource.ArtistCacheDataSource
import com.example.tmdbclient.data.repository.artist.datasource.ArtistLocalDataSource
import com.example.tmdbclient.data.repository.artist.datasource.ArtistRemoteDataSource
import com.example.tmdbclient.domain.repository.ArtistsRepository
import java.lang.Exception

class ArtistRepositoryImpl(
    private val artistRemoteDataSource: ArtistRemoteDataSource,
    private val artistLocalDataSource: ArtistLocalDataSource,
    private val artistCacheDataSource: ArtistCacheDataSource
): ArtistsRepository {

    override suspend fun getArtists(): List<Artist>? {
        return getArtistsFromCache()
    }

    override suspend fun updateArtists(): List<Artist>? {
        val newListOfMovies = getArtistsFromAPI()

        artistLocalDataSource.clear()
        artistLocalDataSource.saveArtistsToDB(newListOfMovies)
        artistCacheDataSource.saveArtistsToCache(newListOfMovies)

        return newListOfMovies
    }

    suspend fun getArtistsFromAPI(): List<Artist> {
        lateinit var artistList: List<Artist>

        try {
            val response = artistRemoteDataSource.getArtists()
            val body = response.body()

            if (body != null) {
                artistList = body.artists
            }
        } catch (e: Exception) {
            Log.i("MyTag", e.message.toString())
        }

        return artistList
    }

    suspend fun getArtistsFromDB(): List<Artist> {
        lateinit var artistList: List<Artist>

        try {
            artistList = artistLocalDataSource.getArtistsFromDB()
        } catch (e: Exception) {
            Log.i("MyTag", e.message.toString())
        }

        if (artistList.isNotEmpty()) {
            return artistList
        } else {
            artistList = getArtistsFromAPI()
            artistLocalDataSource.saveArtistsToDB(artistList)
        }

        return artistList
    }

    suspend fun getArtistsFromCache(): List<Artist> {
        lateinit var artistList: List<Artist>

        try {
            artistList = artistCacheDataSource.getArtistsFromCache()
        } catch (e: Exception) {
            Log.i("MyTag", e.message.toString())
        }

        if (artistList.isNotEmpty()) {
            return artistList
        } else {
            artistList = getArtistsFromDB()
            artistCacheDataSource.saveArtistsToCache(artistList)
        }

        return artistList
    }

}