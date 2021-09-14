package com.mehmetalivargun.watchlist.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.mehmetalivargun.watchlist.R
import com.mehmetalivargun.watchlist.data.response.Result
import com.mehmetalivargun.watchlist.databinding.FragmentMovieListBinding
import com.mehmetalivargun.watchlist.infra.BaseFragment
import com.mehmetalivargun.watchlist.repo.MovieListRepo
import com.mehmetalivargun.watchlist.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieList  : BaseFragment(R.layout.fragment_movie_list) {

    private var _binding:FragmentMovieListBinding?=null
    private val binding get() =_binding!!
    override val viewModel: MovieListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMovieListBinding.inflate(inflater,container,false )
        binding.viewModel=viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.shimmerFrame.startShimmer()
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {

            when (it) {
                true -> {
                    binding.shimmerFrame.visibility=View.VISIBLE
                    binding.genreRV.visibility=View.GONE
                }
                else -> {
                    binding.shimmerFrame.stopShimmer()
                    binding.shimmerFrame.visibility=View.GONE
                    binding.genreRV.visibility=View.VISIBLE
                }
            }
        })
        return binding.root
    }



}