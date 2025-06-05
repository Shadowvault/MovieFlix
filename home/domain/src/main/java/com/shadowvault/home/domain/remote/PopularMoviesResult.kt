package com.shadowvault.home.domain.remote

data class PopularMoviesResult(
    val page: Int,
    val movies: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
