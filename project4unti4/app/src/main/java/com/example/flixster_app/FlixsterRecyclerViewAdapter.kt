package com.example.flixster_app

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FlixsterRecyclerViewAdapter(
    private val movies: List<Flixster>
) : RecyclerView.Adapter<FlixsterRecyclerViewAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_poster, parent, false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val posterImageView: ImageView = mView.findViewById(R.id.poster)

        // Set up the click listener to open the detail activity when clicked
        init {
            posterImageView.setOnClickListener {
                val movie = movies[adapterPosition]
                val intent = Intent(mView.context, DetailActivity::class.java)
                intent.putExtra("name", movie.title)
                intent.putExtra("description", movie.description)
                intent.putExtra("posterUrl", movie.getPosterUrl())
                intent.putExtra("firstair", movie.firstAirDate)
                mView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        // Load the poster into the ImageView using Glide
        Glide.with(holder.itemView.context)
            .load(movie.getPosterUrl())
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .centerCrop()
            .into(holder.posterImageView)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
