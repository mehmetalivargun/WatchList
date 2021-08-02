package com.mehmetalivargun.watchlist.data.response



data class GenreMovies(
    val genre:Genre,
    val movies:List<Result>?
)