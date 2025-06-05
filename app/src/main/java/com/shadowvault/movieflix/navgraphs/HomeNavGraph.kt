package com.shadowvault.movieflix.navgraphs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {

    composable<MainRoute.Dashboard> {
        Box(modifier = Modifier.background(Color.Red).fillMaxSize()) {  }
    }
    composable<MainRoute.Dashboard.MovieDetails> {

    }

}

sealed class MainRoute {
    @Serializable
    data object Dashboard: MainRoute() {
        @Serializable
        data object MovieDetails
    }

}
