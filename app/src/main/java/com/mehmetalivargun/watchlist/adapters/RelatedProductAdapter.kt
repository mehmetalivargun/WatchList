package com.mehmetalivargun.watchlist.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.mehmetalivargun.watchlist.data.response.Result
import com.mehmetalivargun.watchlist.databinding.RelatedMovieBinding


class RelatedProductAdapter : ListAdapter<Result, RelatedProductAdapter.RelatedProductHolder>(DIFF_CALLBACK) {

    var itemClickListener: (Result) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedProductHolder {
        val binding = RelatedMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RelatedProductHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: RelatedProductHolder, position: Int) =
        holder.bind(getItem(position))

    class RelatedProductHolder(
        private val binding: RelatedMovieBinding,
        private val itemClickListener: (Result) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Result) {

            binding.titleText.text = product.title
            binding.priceText.text = product.vote_average.toString()

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500/${product.poster_path}")
                .centerInside()
                .into(binding.imageView)

            binding.root.setOnClickListener {
                itemClickListener(product)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Result, newItem: Result) =
                oldItem == newItem
        }
    }
}