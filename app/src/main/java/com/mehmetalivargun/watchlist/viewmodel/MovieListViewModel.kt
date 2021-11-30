package com.mehmetalivargun.watchlist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mehmetalivargun.watchlist.data.response.Genre
import com.mehmetalivargun.watchlist.data.response.GenreMovies
import com.mehmetalivargun.watchlist.data.response.Result
import com.mehmetalivargun.watchlist.infra.BaseViewModel
import com.mehmetalivargun.watchlist.repo.MovieListRepo
import com.mehmetalivargun.watchlist.ui.MovieDetailsDirections
import com.mehmetalivargun.watchlist.ui.MovieListDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MovieListViewModel @Inject constructor(private val repo: MovieListRepo): BaseViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> = _genres
    private val _genreMovies = MutableLiveData<List<GenreMovies>>()
    val genreMovies: LiveData<List<GenreMovies>> = _genreMovies
    private var listOfMovies:MutableList<GenreMovies> = ArrayList()

    val itemClickListener: (Result) -> Unit = {
        val action = MovieListDirections.actionMovieListToMovieDetails(it.id)
        navigation.navigate(action)
    }
    init {
        getAllGenres()
    }
    private fun getAllGenres()=viewModelScope.launch {

        repo.fetchAllGenres().collect {
            when(it){
                is MovieListRepo.GenreListResult.Success->onGenreSucces(it.results)
                MovieListRepo.GenreListResult.Loading->onLoading()
                MovieListRepo.GenreListResult.UnexpectedError->onUn()
                MovieListRepo.GenreListResult.Failure->onFail()
            }
        }

    }

    private fun onFail() {
        _isLoading.value = true

    }
    private fun onLoading() {
        _isLoading.value = true

    }
    private fun onUn() {
        _isLoading.value = true

    }
    private  fun onGenreSucces(results: List<Genre>) =viewModelScope.launch{
        _genres.postValue(results)
        results.map {genre->
            viewModelScope.async {
                repo.fetchMoviesByGenre(genre.id).collect {
                    when(it){
                        is MovieListRepo.MovieListResult.Success-> {
                            listOfMovies.add(GenreMovies(genre,it.results))
                        }
                        MovieListRepo.MovieListResult.Loading->onLoading()
                    }
                }
                // Logic
            }
        }.forEach {
            it.await()
        }
        _genreMovies.postValue(listOfMovies)
        _isLoading.value = false
    }
}