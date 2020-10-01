package com.example.tmdbclient.data.repository.artist.datasource

import com.example.tmdbclient.data.model.artist.ArtistList
import com.example.tmdbclient.data.model.movie.MovieList
import retrofit2.Response

interface ArtistRemoteDataSource {

    suspend fun getArtists(): Response<ArtistList>

}