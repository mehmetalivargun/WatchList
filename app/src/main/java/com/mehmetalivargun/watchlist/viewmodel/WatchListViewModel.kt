package com.mehmetalivargun.watchlist.viewmodel

import android.graphics.Movie
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mehmetalivargun.watchlist.data.db.MovieEntity
import com.mehmetalivargun.watchlist.data.response.GenreMovies
import com.mehmetalivargun.watchlist.data.response.MovieResponse
import com.mehmetalivargun.watchlist.data.response.Result
import com.mehmetalivargun.watchlist.infra.BaseViewModel
import com.mehmetalivargun.watchlist.repo.MovieDetailsRepo
import com.mehmetalivargun.watchlist.repo.MovieListRepo
import com.mehmetalivargun.watchlist.repo.WatchListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(private val repo: WatchListRepo) : BaseViewModel() {
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading
    private var listOfMovies: MutableList<MovieResponse> = ArrayList()
    private val _movies = MutableLiveData<List<MovieResponse>>()
    val movies: LiveData<List<MovieResponse>> = _movies

    init {
        getMyList()
    }

    fun deleteTodo(todo: MovieResponse) = viewModelScope.launch {

        listOfMovies.remove(todo)
        repo.delete(MovieEntity(todo.id, todo.title))

    }

    private fun getMyList() = viewModelScope.launch {
        repo.getList().forEach { movie ->
            repo.fetchMovieDetails(movie.id).collect {
                when (it) {
                    is MovieDetailsRepo.MovieResponseResult.Success -> {
                        onSucces(it.result)
                    }
                    MovieDetailsRepo.MovieResponseResult.Loading -> onLoading()
                    else->onFail()
                }
            }
        }
        _movies.postValue(listOfMovies)
    }

    private fun onFail(){
        _isLoading.value=false
    }

    private fun onLoading() {
        _isLoading.value = true
    }

    private fun onSucces(result: MovieResponse) {
        _isLoading.value = false
        listOfMovies.add(result)
    }
}