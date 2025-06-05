package com.shadowvault.home.presentation

sealed interface HomeScreenAction {
    data object LoadNextPopularMovies: HomeScreenAction
    data class OnMoviePress(val movieId: Int): HomeScreenAction
    data class OnMovieFavoritePress(val movieId: Int): HomeScreenAction
}