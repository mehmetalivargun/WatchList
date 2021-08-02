package com.mehmetalivargun.watchlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalivargun.watchlist.R
import com.mehmetalivargun.watchlist.data.response.Genre
import com.mehmetalivargun.watchlist.data.response.GenreMovies
import com.mehmetalivargun.watchlist.databinding.GenreItemBinding
class CustomViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
class GenreRVAdapter : ListAdapter<GenreMovies, CustomViewHolder>(Companion) {
    private val viewPool = RecyclerView.RecycledViewPool()

    companion object : DiffUtil.ItemCallback<GenreMovies>() {
        override fun areItemsTheSame(oldItem: GenreMovies, newItem: GenreMovies): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GenreMovies, newItem: GenreMovies): Boolean {
            return  oldItem.genre.id == newItem.genre.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GenreItemBinding.inflate(inflater, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBookCategory = getItem(position)
        val itemBinding = holder.binding as GenreItemBinding

        itemBinding.genreMovies = currentBookCategory
        itemBinding.nestedRecyclerView.setRecycledViewPool(viewPool)
        itemBinding.executePendingBindings()
    }
}