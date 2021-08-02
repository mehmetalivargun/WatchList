package com.mehmetalivargun.watchlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalivargun.watchlist.data.response.Result
import com.mehmetalivargun.watchlist.databinding.ItemMovieGenreBinding
import com.mehmetalivargun.watchlist.databinding.MovieItemBinding

class MovieHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)


class MovieAdapter : ListAdapter< Result, MovieHolder>(Companion) {
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
        val currentBook = getItem(position)
        val itemBinding = holder.binding as ItemMovieGenreBinding
        itemBinding.movie = currentBook
        itemBinding.executePendingBindings()
    }
}