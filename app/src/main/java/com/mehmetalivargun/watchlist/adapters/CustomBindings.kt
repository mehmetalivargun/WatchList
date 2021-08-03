package com.mehmetalivargun.watchlist.adapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.mehmetalivargun.watchlist.data.response.GenreMovies
import com.mehmetalivargun.watchlist.data.response.Result

val genreAdapter=GenreRVAdapter()
val relatedProductAdapter=RelatedProductAdapter()
val movieAdapter=MovieAdapter()
@BindingAdapter("app:genres")
fun RecyclerView.genres(genres: List<GenreMovies>?) {
    if (adapter != genreAdapter) {
        adapter = genreAdapter
    }



    genreAdapter.submitList(genres.orEmpty())

}
@BindingAdapter(value = ["setMovies"])
fun RecyclerView.setMovies(books: List<Result>?) {
    if (books != null) {
        val bookAdapter = MovieAdapter()
        bookAdapter.submitList(books)

        adapter = bookAdapter
    }
}


    @BindingAdapter("app:imageUrl")
fun ImageView.imageUrl(imageUrl: String?) {
    val image= "https://image.tmdb.org/t/p/w500/$imageUrl"
    image?.let {
        Glide.with(this)
            .load(it)
            .into(this)
    }
}

@BindingAdapter("app:relatedMovie","app:relatedItemClickListener")
fun RecyclerView.relatedProducts(
    products: List<Result>?,
    itemClickListener: (Result) -> Unit
) {
    if (adapter != relatedProductAdapter) {
        adapter = relatedProductAdapter
    }

    relatedProductAdapter.itemClickListener = itemClickListener

    if (itemDecorationCount == 0) {
        addItemDecoration(RelatedProductDecoration())
    }

    relatedProductAdapter.submitList(products.orEmpty())
}
