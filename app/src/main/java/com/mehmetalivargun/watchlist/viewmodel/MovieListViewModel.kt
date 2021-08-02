package com.mehmetalivargun.watchlist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mehmetalivargun.watchlist.data.response.Genre
import com.mehmetalivargun.watchlist.data.response.GenreMovies
import com.mehmetalivargun.watchlist.infra.BaseViewModel
import com.mehmetalivargun.watchlist.repo.MovieListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
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



    init {
        getAllGenres()
    }




    private fun getAllGenres()=viewModelScope.launch {
        Log.e("Error","here")
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
        Log.e("Error","Fail")
        _isLoading.value = true

    }



    private fun onLoading() {
        Log.e("Error","Loading")
        _isLoading.value = true

    }

    private fun onUn() {
        Log.e("Error","Un")
        _isLoading.value = true

    }

    private  fun onGenreSucces(results: List<Genre>)=viewModelScope.launch {
        _genres.postValue(results)
        Log.e("GenreListt",results.size.toString())
        results.forEach { genre ->

            repo.fetchMoviesByGenre(genre.id).collect {
                Log.e("GenreListt","Here1")
                when(it){
                    is MovieListRepo.MovieListResult.Success-> {
                        listOfMovies.add(GenreMovies(genre,it.results))

                    }
                    MovieListRepo.MovieListResult.Loading->onLoading()
                }
            }

        }

        Log.e("GenreListt",listOfMovies.size.toString())
        _genreMovies.postValue(listOfMovies)

        _isLoading.value = false
    }
}