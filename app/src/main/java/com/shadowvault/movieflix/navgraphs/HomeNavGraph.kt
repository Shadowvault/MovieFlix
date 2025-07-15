package com.shadowvault.movieflix.navgraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.shadowvault.home.presentation.HomeScreenRoot
import com.shadowvault.movie_details.presentation.MovieDetailsScreenRoot
import kotlinx.serialization.Serializable

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {

    composable<MainRoute.Home> {
        HomeScreenRoot(
            onMovieClicked = { movieId, isLiked ->
                navController.navigate(MainRoute.Home.MovieDetails(movieId, isLiked))
            }
        )
    }
    composable<MainRoute.Home.MovieDetails> {
        val arg = it.toRoute<MainRoute.Home.MovieDetails>()
        MovieDetailsScreenRoot(
            movieId = arg.movieInt,
            isLiked = arg.isLiked,
            onBackButtonPress = { navController.navigateUp() }
        )
    }
}

sealed class MainRoute {
    @Serializable
    data object Home : MainRoute() {
        @Serializable
        data class MovieDetails(val movieInt: Int, val isLiked: Boolean)
    }
}
