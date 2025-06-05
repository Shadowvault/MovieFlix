package com.shadowvault.auth.presentation.login

import android.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shadowvault.core.presentation.designsystem.MovieFlixTheme
import com.shadowvault.core.presentation.ui.ObserveAsEvents
import com.shadowvault.core.presentation.ui.util.launchCustomTab
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginScreenViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is LoginScreenEvent.Error -> {
                AlertDialog.Builder(context).apply {
                    setTitle(event.errorAlertText.title.asString(context))
                    setMessage(event.errorAlertText.description.asString(context))
                }.show()
            }

            is LoginScreenEvent.OpenBrowser -> {
                launchCustomTab(context = context, url = event.token)
            }

            else -> Unit
        }
    }
    LoginScreen(
        state = state,
        onAction = { action ->
            when (action) {
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun LoginScreen(
    state: LoginScreenState,
    onAction: (LoginScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Button({ onAction(LoginScreenAction.OnLoginButtonPress) }) { }
    }
    Text("")
}

@Preview
@Composable
private fun LoginScreenPreview() {
    MovieFlixTheme {
        Surface {
            LoginScreen(
                state = LoginScreenState(
                    isLoading = false
                ),
                onAction = {}
            )
        }
    }
}