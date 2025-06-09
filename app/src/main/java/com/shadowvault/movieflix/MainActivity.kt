package com.shadowvault.movieflix

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.shadowvault.core.presentation.designsystem.MovieFlixTheme
import com.shadowvault.movieflix.navgraphs.RootNavGraph
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isCheckingAuth
            }
        }

        setContent {
            MovieFlixTheme {
                if (!viewModel.state.isCheckingAuth) {
                    val navController = rememberNavController()
                    RootNavGraph(
                        navController = navController,
                        isLoggedIn = viewModel.state.isLoggedIn
                    )
                }
            }
        }
    }

    private fun handleIntent(intent: Intent?) {
        if (intent != null && intent.action == Intent.ACTION_VIEW) {
            viewModel.onAction(MainAction.OnHandleDeepLink(intent.data))
        }
    }
}
