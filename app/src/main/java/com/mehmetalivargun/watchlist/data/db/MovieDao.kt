package com.mehmetalivargun.watchlist.data.db

import androidx.room.*
import com.mehmetalivargun.watchlist.data.response.Result

@Dao
interface MovieDao {
    @Query("SELECT * FROM watchlist")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM watchlist WHERE id = :id")
    suspend fun getById(id: Int): MovieEntity?

    @Delete
     suspend fun deleteMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
}