package com.shadowvault.home.presentation

import com.shadowvault.home.domain.remote.Movie

data class HomeScreenState(
    val isLoading: Boolean = false,
    val popularMovies: List<Movie> = emptyList()
)
