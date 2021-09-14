package com.mehmetalivargun.watchlist.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.mehmetalivargun.watchlist.data.response.MovieResponse
import com.mehmetalivargun.watchlist.data.response.Result
import com.mehmetalivargun.watchlist.databinding.ItemWatchListBinding
import com.mehmetalivargun.watchlist.infra.navigation.Navigation


class ListMovieHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)




class ListMovieAdapter() : RecyclerView.Adapter<ListMovieHolder>() {


     fun takeItem(position: Int):MovieResponse{
         Log.e("What",differ.currentList[position].toString())
        return differ.currentList[position]
    }


    var itemClickListener: (Result) -> Unit = {}
    val navigation = Navigation()



    private val diffCallBack=object : DiffUtil.ItemCallback<MovieResponse>() {
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return  oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return  oldItem == newItem
        }

    }

    val differ= AsyncListDiffer(this,diffCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMovieHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWatchListBinding.inflate(inflater, parent, false)
        val imageView: ShapeableImageView = binding.movieImage
        val radius=20.0

        binding.movieImage.shapeAppearanceModel = imageView.shapeAppearanceModel
            .toBuilder()
            .setAllCornerSizes(radius.toFloat())
            .build()

        return ListMovieHolder(binding)
    }


    override fun onBindViewHolder(holder: ListMovieHolder, position: Int) {
      /*  var navController: NavController? = null


        holder.apply {

            with(holder.itemView) {



                itemView.setOnClickListener {

                    navController = androidx.navigation.Navigation.findNavController(itemView)
                    val action = MovieListDirections.actionMovieListToMovieDetails(getItem(position).id)
                    Log.e("Action",getItem(position).toString())
                    navController!!.navigate(action)

                }

            }

        }*/


      /*  holder.itemView.setOnClickListener {

            val result =getItem(position)
            val action = MovieListDirections.actionMovieListToMovieDetails(result.id)
            Log.e("Click",action.toString())

            navigation.navigate(action)

        }*/
        val currentBook = takeItem(position)
        val itemBinding = holder.binding as ItemWatchListBinding
        itemBinding.movie= currentBook
        itemBinding.deleteproduct.setOnClickListener {

            Log.e("DenemeTT","Here")
            notifyDataSetChanged()

        }
        itemBinding.executePendingBindings()
    }


    override fun getItemCount(): Int {
        val size =differ.currentList.size
        Log.e("ErrorSize",size.toString())
        return size
    }


}