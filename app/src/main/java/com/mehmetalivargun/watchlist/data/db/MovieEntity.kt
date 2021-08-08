package com.mehmetalivargun.watchlist.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class MovieEntity(

    @PrimaryKey val id: Int,
    val title: String
)
