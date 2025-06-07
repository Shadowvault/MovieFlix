package com.shadowvault.home.presentation

import androidx.paging.PagingData
import com.shadowvault.core.domain.movies.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeScreenState(
    val isLoading: Boolean = false,
    val popularMovies: Flow<PagingData<Movie>> = emptyFlow(),
)
