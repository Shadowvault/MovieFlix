package com.shadowvault.movie_details.presentation

sealed interface MovieDetailsScreenAction {
    data class Init(val movieId: Int, val isLiked: Boolean) : MovieDetailsScreenAction
    data object OnBackButtonPress : MovieDetailsScreenAction
    data object OnMovieFavoritePress: MovieDetailsScreenAction
}