package com.enesgemci.dubbizle.movies.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.enesgemci.dubbizle.R
import com.enesgemci.dubbizle.helper.GlideApp
import com.enesgemci.dubbizle.movies.ui.model.ui.MovieUIModel

class MoviesAdapter(
    private val context: Context,
    private val onClickFunc: (sharedView: View, movie: MovieUIModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    private var movies: ArrayList<MovieUIModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MoviesViewHolder(layoutInflater.inflate(R.layout.item_movies, null))

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoviesViewHolder).setMovie(movies[position])
    }

    fun updateMovies(list: ArrayList<MovieUIModel>, page: Int) {
        if (page == 1) {
            movies.clear()
        }

        val oldSize = itemCount
        movies.addAll(list)
        notifyItemRangeInserted(oldSize, movies.size - 1)
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageViewPoster = itemView.findViewById<ImageView>(R.id.imageViewPoster)
        private val textViewVote = itemView.findViewById<TextView>(R.id.textViewVote)

        fun setMovie(movie: MovieUIModel) {
            GlideApp.with(context)
                .load(movie.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewPoster)

            textViewVote.text = "${movie.voteAverage}/10"

            imageViewPoster.setOnClickListener {
                onClickFunc(imageViewPoster, movie)
            }
        }
    }
}