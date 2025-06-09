package com.shadowvault.home.presentation

import androidx.paging.compose.LazyPagingItems
import com.shadowvault.core.domain.movies.Movie

sealed interface HomeScreenAction {
    data class OnForceRefresh(val pagedMovies: LazyPagingItems<Movie>) : HomeScreenAction
    data class OnMoviePress(val movieId: Int, val isLiked: Boolean) : HomeScreenAction
    data class OnMovieFavoritePress(val movieId: Int, val isLiked: Boolean) : HomeScreenAction
    data object OnRefreshStateDone : HomeScreenAction
}
