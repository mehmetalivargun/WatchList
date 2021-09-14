package com.mehmetalivargun.watchlist.repo

import android.util.Log
import com.mehmetalivargun.watchlist.data.api.TMDBapi
import com.mehmetalivargun.watchlist.data.db.MovieDao
import com.mehmetalivargun.watchlist.data.db.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class WatchListRepo  @Inject constructor(private val api: TMDBapi, private val dao: MovieDao){

    suspend fun getList()=dao.getAll()

    suspend fun delete(entity: MovieEntity)=dao.deleteMovie(entity)


    suspend fun  fetchMovieDetails(id : Int): Flow<Any> = flow {

        emit(MovieDetailsRepo.MovieResponseResult.Loading)
        val response = try {
            api.getMovieById(id)

        }catch (ex: Exception){
            null
        }

        Log.e("Denemne",response?.body()!!.imdb_id)

        when(response?.code()){
            null -> emit(MovieDetailsRepo.MovieResponseResult.Failure)
            200 -> {
                val movie = response.body()!!

                emit(MovieDetailsRepo.MovieResponseResult.Success(movie))

            }
            else -> emit(MovieDetailsRepo.MovieResponseResult.UnexpectedError)
        }
    }

}