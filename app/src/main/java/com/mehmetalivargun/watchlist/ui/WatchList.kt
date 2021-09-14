package com.mehmetalivargun.watchlist.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalivargun.watchlist.R
import com.mehmetalivargun.watchlist.adapters.ListMovieAdapter
import com.mehmetalivargun.watchlist.data.db.MovieEntity
import com.mehmetalivargun.watchlist.data.response.MovieResponse
import com.mehmetalivargun.watchlist.databinding.FragmentMovieDetailsBinding
import com.mehmetalivargun.watchlist.databinding.FragmentMovieListBinding
import com.mehmetalivargun.watchlist.databinding.FragmentWatchListBinding
import com.mehmetalivargun.watchlist.infra.BaseFragment
import com.mehmetalivargun.watchlist.viewmodel.MovieListViewModel
import com.mehmetalivargun.watchlist.viewmodel.WatchListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WatchList : BaseFragment(R.layout.fragment_watch_list) {

    private var _binding: FragmentWatchListBinding?=null
    private val binding get() =_binding!!
    override val viewModel: WatchListViewModel by viewModels()
     lateinit var movieAdapter: ListMovieAdapter
    private lateinit var movies:List<MovieResponse>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentWatchListBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.shimmerFrame.startShimmer()
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {

                    binding.shimmerFrame.visibility=View.VISIBLE
                    binding.movies.visibility=View.GONE

                }
                else -> {
                    binding.shimmerFrame.stopShimmer()
                    binding.shimmerFrame.visibility=View.GONE
                    binding.movies.visibility=View.VISIBLE
                }
            }
        })
        // Inflate the layout for this fragment
        setupRecycler()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        swipeToDelete()
    }

    private fun setupRecycler() {
        movieAdapter=ListMovieAdapter()
        binding.movies.apply {
            adapter=movieAdapter
            layoutManager= LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

        viewModel.movies.observe(viewLifecycleOwner,{it->
            movies=it
            updateUI(it)
            movieAdapter.differ.submitList(it)

        })


    }

    private fun updateUI(listTodo: List<MovieResponse>) {
        if(listTodo.isNotEmpty()){
            binding.movies.visibility=View.VISIBLE

        }else{
            binding.movies.visibility=View.GONE

        }
    }

    private fun swipeToDelete(){
        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or  ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movie=(movieAdapter.takeItem(viewHolder.adapterPosition))
                viewModel.deleteTodo(movie)
                movieAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

        }).attachToRecyclerView(binding.movies)
    }




}