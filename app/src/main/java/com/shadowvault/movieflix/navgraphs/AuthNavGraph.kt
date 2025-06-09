package com.shadowvault.movieflix.navgraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shadowvault.auth.presentation.login.LoginScreenRoot
import kotlinx.serialization.Serializable

fun NavGraphBuilder.authNavGraph() {
    composable<AuthRoute.Login> {
        LoginScreenRoot()
    }
}

sealed class AuthRoute {

    @Serializable
    data object Login : AuthRoute()
}
