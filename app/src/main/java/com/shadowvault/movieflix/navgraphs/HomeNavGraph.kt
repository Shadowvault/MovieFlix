package com.shadowvault.movieflix.navgraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.shadowvault.home.presentation.HomeScreenRoot
import kotlinx.serialization.Serializable

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {

    composable<MainRoute.Home> {
        HomeScreenRoot(
            onMovieClicked = {

            }
        )
    }
    composable<MainRoute.Home.MovieDetails> {

    }

}

sealed class MainRoute {
    @Serializable
    data object Home: MainRoute() {
        @Serializable
        data object MovieDetails
    }

}
