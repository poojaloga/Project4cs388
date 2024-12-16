package com.example.flixster_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get the data from the intent
        val title = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val posterUrl = intent.getStringExtra("posterUrl")
        val firstair = intent.getStringExtra("firstair")

        // Find the views and set the data
        val titleTextView = findViewById<TextView>(R.id.name)
        val descriptionTextView = findViewById<TextView>(R.id.description)
        val posterImageView = findViewById<ImageView>(R.id.poster)
        val firstairdate = findViewById<TextView>(R.id.firstair)

        titleTextView.text = title
        descriptionTextView.text = description
        firstairdate.text = "First Air Date: $firstair"

        // Load the poster image with Glide
        Glide.with(this)
            .load(posterUrl)
            .into(posterImageView)
    }
}
