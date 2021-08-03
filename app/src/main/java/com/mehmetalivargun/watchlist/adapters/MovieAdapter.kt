package com.mehmetalivargun.watchlist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalivargun.watchlist.R


import com.mehmetalivargun.watchlist.data.response.Result
import com.mehmetalivargun.watchlist.databinding.ItemMovieGenreBinding
import com.mehmetalivargun.watchlist.infra.navigation.Navigation
import com.mehmetalivargun.watchlist.ui.MovieDetailsDirections
import com.mehmetalivargun.watchlist.ui.MovieList
import com.mehmetalivargun.watchlist.ui.MovieListDirections


class MovieHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)




class MovieAdapter : ListAdapter< Result, MovieHolder>(Companion) {
    var itemClickListener: (Result) -> Unit = {}
    val navigation = Navigation()


    companion object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return  oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieGenreBinding.inflate(inflater, parent, false)

        return MovieHolder(binding)
    }



    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        var navController: NavController? = null

        holder.apply {

            with(holder.itemView) {



                itemView.setOnClickListener {

                    navController = androidx.navigation.Navigation.findNavController(itemView)
                    val action = MovieListDirections.actionMovieListToMovieDetails(getItem(position).id)
                    Log.e("Action",getItem(position).toString())
                    navController!!.navigate(action)

                }

            }

        }


      /*  holder.itemView.setOnClickListener {

            val result =getItem(position)
            val action = MovieListDirections.actionMovieListToMovieDetails(result.id)
            Log.e("Click",action.toString())

            navigation.navigate(action)

        }*/
        val currentBook = getItem(position)
        val itemBinding = holder.binding as ItemMovieGenreBinding
        itemBinding.movie= currentBook
        itemBinding.executePendingBindings()
    }
}