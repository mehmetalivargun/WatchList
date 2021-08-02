package com.mehmetalivargun.watchlist.data.api

import com.mehmetalivargun.watchlist.data.response.Genres
import com.mehmetalivargun.watchlist.data.response.MovieResponse
import com.mehmetalivargun.watchlist.data.response.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBapi {
    @GET("/3/movie/popular?api_key=66759c0f486b672b55c72aaa440fbc28&language=en-US&page=1")
    suspend fun listAllPopularMovies(): Response<MoviesResponse>


    @GET("/3/movie/{id}?api_key=66759c0f486b672b55c72aaa440fbc28&language=en-US&page=1")
    suspend fun getMovieById(@Path("id") id: Int): Response<MovieResponse>

    @GET("/3/movie/{id}/recommendations?api_key=66759c0f486b672b55c72aaa440fbc28&language=en-US&page=1")
    suspend fun getSimilarByID(@Path("id") id: Int): Response<MoviesResponse>

    @GET("/3/discover/movie?api_key=66759c0f486b672b55c72aaa440fbc28&language=en-US")
    suspend fun getByGenre( @Query("with_genres") genre:Int): Response<MoviesResponse>

    @GET("/3/genre/movie/list?api_key=66759c0f486b672b55c72aaa440fbc28&language=en-US&page=1")
    suspend fun getAllGenres(): Response<Genres>
}