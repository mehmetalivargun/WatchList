package com.mehmetalivargun.watchlist.repo

import android.util.Log
import com.mehmetalivargun.watchlist.data.api.TMDBapi
import com.mehmetalivargun.watchlist.data.response.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MovieDetailsRepo @Inject constructor(private val api: TMDBapi) {

    suspend fun  fetchMovieDetails(id : Int): Flow<Any> = flow {

        emit(MovieResponseResult.Loading)
        val response = try {
            api.getMovieById(id)

        }catch (ex: Exception){
            null
        }

        Log.e("Denemne",response?.body()!!.imdb_id)

        when(response?.code()){
            null -> emit(MovieResponseResult.Failure)
            200 -> {
                val movie = response.body()!!

                emit(MovieResponseResult.Success(movie))

            }
            else -> emit(MovieResponseResult.UnexpectedError)
        }
    }

    suspend fun fetchRelatedMovies(id:Int):Flow<Any> = flow {

        emit(MovieResponseResult.Loading)
        val response = try {
            api.getSimilarByID(id)


        }catch (ex: Exception){
            null
        }
        Log.e("Related",response?.code().toString())

        when(response?.code()){
            null -> emit(MovieListRepo.MovieListResult.Failure)
            200 -> {
                val movie = response.body()!!.results
                Log.e("Error",movie.size.toString()+"Deneme")
                emit(MovieListRepo.MovieListResult.Success(movie))

            }
            else -> emit(MovieListRepo.MovieListResult.UnexpectedError)
        }

    }

    sealed class MovieResponseResult {
        class Success(val result: MovieResponse) : MovieResponseResult()
        object Failure : MovieResponseResult()
        object UnexpectedError : MovieResponseResult()
        object Loading : MovieResponseResult()
    }
}