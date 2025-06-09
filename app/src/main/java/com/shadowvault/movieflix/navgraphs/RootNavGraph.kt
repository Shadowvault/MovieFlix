package com.shadowvault.movieflix.navgraphs

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RootNavGraph(navController: NavHostController, isLoggedIn: Boolean) {

    Scaffold { paddingValues ->
        val layoutDirection = LocalLayoutDirection.current
        SharedTransitionLayout {
            NavHost(
                modifier = Modifier.padding(
                    top = 0.dp,
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(layoutDirection),
                    end = paddingValues.calculateEndPadding(layoutDirection)
                ),
                navController = navController,
                startDestination = if (isLoggedIn) MainRoute.Home else AuthRoute.Login
            ) {
                authNavGraph()
                homeNavGraph(navController = navController)
            }
        }
    }
}
