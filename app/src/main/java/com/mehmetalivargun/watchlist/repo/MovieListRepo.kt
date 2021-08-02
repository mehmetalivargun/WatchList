package com.mehmetalivargun.watchlist.repo

import com.mehmetalivargun.watchlist.data.api.TMDBapi
import com.mehmetalivargun.watchlist.data.response.Genre
import com.mehmetalivargun.watchlist.data.response.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MovieListRepo  @Inject constructor(private val api: TMDBapi){


    suspend fun  fetchAllGenres(): Flow<Any> = flow {

        emit(GenreListResult.Loading)
        val response = try {
            api.getAllGenres()

        }catch (ex: Exception){
            null
        }

        when(response?.code()){
            null -> emit(GenreListResult.Failure)
            200 -> {
                val movies = response.body()!!

                emit(GenreListResult.Success(movies.genres))

            }
            else -> emit(GenreListResult.UnexpectedError)
        }
    }
    suspend fun  fetchMoviesByGenre(id : Int): Flow<Any> = flow {


        emit(MovieListResult.Loading)
        val response = try {
            api.getByGenre(id)

        }catch (ex:Exception){
            null
        }


        when(response?.code()){
            null -> emit(MovieListResult.Failure)
            200 -> {
                val movies = response.body()!!.results
                emit(MovieListResult.Success(movies))

            }
            else -> emit(MovieListResult.UnexpectedError)
        }
    }

    sealed class MovieListResult {
        class Success(val results: List<Result>) : MovieListResult()
        object Failure : MovieListResult()
        object UnexpectedError : MovieListResult()
        object Loading : MovieListResult()
    }



    sealed class GenreListResult {
        class Success(val results: List<Genre>) : GenreListResult()
        object Failure : GenreListResult()
        object UnexpectedError : GenreListResult()
        object Loading : GenreListResult()
    }


}