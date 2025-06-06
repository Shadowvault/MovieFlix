package com.shadowvault.movie_details.presentation

sealed interface MovieDetailsScreenAction {
    data class Init(val movieId: Int) : MovieDetailsScreenAction
    data object OnBackButtonPress : MovieDetailsScreenAction
}