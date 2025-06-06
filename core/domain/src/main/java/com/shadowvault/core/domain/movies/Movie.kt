package com.shadowvault.core.domain.movies

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val isAdult: Boolean,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val genreIds: List<Int>,
    val isVideo: Boolean,
    val isLiked: Boolean = false
)
