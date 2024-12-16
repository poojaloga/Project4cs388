package com.example.flixster_app

class Flixster(
    val title: String?,
    val description: String?,
    val posterPath: String?,
    val firstAirDate: String?

) {
    fun getPosterUrl(): String {
        return "https://image.tmdb.org/t/p/w500/$posterPath"
    }
}
