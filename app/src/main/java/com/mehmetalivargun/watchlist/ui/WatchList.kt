package com.mehmetalivargun.watchlist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mehmetalivargun.watchlist.R
import com.mehmetalivargun.watchlist.databinding.FragmentMovieDetailsBinding
import com.mehmetalivargun.watchlist.databinding.FragmentMovieListBinding
import com.mehmetalivargun.watchlist.databinding.FragmentWatchListBinding
import com.mehmetalivargun.watchlist.infra.BaseFragment
import com.mehmetalivargun.watchlist.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchList : BaseFragment(R.layout.fragment_watch_list) {

    private var _binding: FragmentWatchListBinding?=null
    private val binding get() =_binding!!
    override val viewModel: MovieListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentWatchListBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        // Inflate the layout for this fragment
        return binding.root
    }


}