package com.shadowvault.core.domain.movies

data class MoviesResult(
    val page: Int,
    val movies: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
