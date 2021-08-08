package com.mehmetalivargun.watchlist.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.mehmetalivargun.watchlist.R
import com.mehmetalivargun.watchlist.data.db.MovieDao
import com.mehmetalivargun.watchlist.data.db.MovieEntity
import com.mehmetalivargun.watchlist.data.response.MovieResponse
import com.mehmetalivargun.watchlist.data.response.Result
import com.mehmetalivargun.watchlist.infra.BaseViewModel
import com.mehmetalivargun.watchlist.infra.snackbar.SnackbarAction
import com.mehmetalivargun.watchlist.infra.snackbar.SnackbarState
import com.mehmetalivargun.watchlist.repo.MovieDetailsRepo
import com.mehmetalivargun.watchlist.repo.MovieListRepo
import com.mehmetalivargun.watchlist.ui.MovieDetailsDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repo: MovieDetailsRepo,savedStateHandle: SavedStateHandle): BaseViewModel()  {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _movie = MutableLiveData<MovieResponse>()
    val movie: LiveData<MovieResponse> = _movie
    private val _movies = MutableLiveData<List<Result>>()
    val movies: LiveData<List<Result>> = _movies
    var genres: String? =null

    val itemClickListener: (Result) -> Unit = {
        val action = MovieDetailsDirections.actionMovieDetailsSelf(it.id)
        navigation.navigate(action)
    }
    init {
        val yourArgument: Int? = savedStateHandle["movieID"]
        Log.e("movieID",yourArgument.toString())
        if (yourArgument != -1 &&yourArgument != null) {
            getMovieDetails(yourArgument)
            getSimilarMovies(yourArgument)
        }
        else{
            getMovieDetails(28)
            getSimilarMovies(28)
        }

    }


    private  fun getMovieDetails(id :Int)=viewModelScope.launch {
        repo.fetchMovieDetails(id).collect {
            when(it){
                is MovieDetailsRepo.MovieResponseResult.Success->onSuccess(it.result)
                MovieDetailsRepo.MovieResponseResult.Loading->onLoading()
            }
        }
    }


    fun onAddButtonClick()=viewModelScope.launch {
        repo.insertTodo(MovieEntity(movie.value!!.id,movie.value!!.title))
        _snackbarState.value = SnackbarState(
            errorRes = R.string.added,
            duration = Snackbar.LENGTH_INDEFINITE,
            action = null

        )


    }


    private  fun getSimilarMovies(id :Int)=viewModelScope.launch {
        repo.fetchRelatedMovies(id).collect {
            when(it){
                is MovieListRepo.MovieListResult.Success->onRelatedSuccess(it.results)
                MovieListRepo.MovieListResult.Loading->onRelatedLoading()
                MovieListRepo.MovieListResult.Failure->onRelatedLoading()
            }
        }
        Log.e("Error","error")
    }


    private fun onLoading() {
        _isLoading.value = true


    }
    private fun onRelatedLoading() {
        _isLoading.value = true
        Log.e("Error","error2")

    }

    private fun onSuccess(result: MovieResponse) {
        _isLoading.value = false
        Log.e("movieIDR",result.toString())

        _movie.postValue(result)
        genres= movie.value?.genres?.get(0)?.toString()

    }

    private fun onRelatedSuccess(result:  List<Result>) {
        Log.e("Error","error1")
        Log.e("movieIDL",result.toString())
        if(result.isNotEmpty()){
            _movies.postValue(result.subList(0,6))
        }



    }

}