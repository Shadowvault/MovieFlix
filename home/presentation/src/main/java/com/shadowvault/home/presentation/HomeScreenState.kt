package com.shadowvault.home.presentation

import com.shadowvault.core.domain.movies.Movie

data class HomeScreenState(
    val isLoading: Boolean = false,
    val popularMovies: List<Movie> = emptyList()
)
