package com.example.flixster_app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

private const val API_KEY = "fa34fb10fe7189bd0676547c5f9bca06"

class FlixsterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flixster, container, false)

        // Get both RecyclerViews from the layout
        val tvShowsRecyclerView = view.findViewById<RecyclerView>(R.id.list)
        val moviesRecyclerView = view.findViewById<RecyclerView>(R.id.movies_list)

        // Set RecyclerViews to horizontal scrolling
        tvShowsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        moviesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Fetch and display TV shows and movies
        fetchTVShowsAndUpdateAdapter(tvShowsRecyclerView)
        fetchMoviesAndUpdateAdapter(moviesRecyclerView)

        return view
    }

    private fun fetchTVShowsAndUpdateAdapter(recyclerView: RecyclerView) {
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        // Fetch popular TV shows
        client.get("https://api.themoviedb.org/3/tv/popular", params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("FlixsterFragment", "Fetched TV shows: ${json.jsonObject}")
                val resultsArray = json.jsonObject.getJSONArray("results")
                val tvShows = mutableListOf<Flixster>()

                for (i in 0 until resultsArray.length()) {
                    val tvShowJSON = resultsArray.getJSONObject(i)
                    val tvShow = Flixster(
                        tvShowJSON.optString("name"), // TV show name
                        tvShowJSON.optString("overview"), // TV show description
                        tvShowJSON.optString("poster_path"), // TV show poster
                        tvShowJSON.optString("first_air_date") // First air date for TV shows
                    )
                    tvShows.add(tvShow)
                }

                // Set the adapter with the fetched TV shows
                recyclerView.adapter = FlixsterRecyclerViewAdapter(tvShows)
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                Log.e("FlixsterFragment", "Failed to fetch TV shows: $errorResponse")
            }
        })
    }

    private fun fetchMoviesAndUpdateAdapter(recyclerView: RecyclerView) {
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        // Fetch popular movies
        client.get("https://api.themoviedb.org/3/movie/popular", params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("FlixsterFragment", "Fetched movies: ${json.jsonObject}")
                val resultsArray = json.jsonObject.getJSONArray("results")
                val movies = mutableListOf<Flixster>()

                for (i in 0 until resultsArray.length()) {
                    val movieJSON = resultsArray.getJSONObject(i)
                    val movie = Flixster(
                        movieJSON.optString("title"), // Movie title
                        movieJSON.optString("overview"), // Movie description
                        movieJSON.optString("poster_path"), // Movie poster
                        movieJSON.optString("release_date") // Release date for movies
                    )
                    movies.add(movie)
                }

                // Set the adapter with the fetched movies
                recyclerView.adapter = FlixsterRecyclerViewAdapter(movies)
            }

            override fun onFailure(statusCode: Int, headers: Headers?, errorResponse: String, t: Throwable?) {
                Log.e("FlixsterFragment", "Failed to fetch movies: $errorResponse")
            }
        })
    }
}

