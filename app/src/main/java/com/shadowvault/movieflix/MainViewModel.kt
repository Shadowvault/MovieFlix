package com.shadowvault.movieflix

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowvault.core.domain.session.SessionStorage
import com.shadowvault.core.domain.util.fold
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val loginUseCase: LoginUseCase,
    private val sessionStorage: SessionStorage,
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    private val eventChannel = Channel<MainEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)
            state = state.copy(
                isLoggedIn = sessionStorage.get() != null
            )
            state = state.copy(isCheckingAuth = false)
        }
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnHandleDeepLink -> {
                if (action.uri == null) return
                handleSchemeTMDBRedirect(
                    uri = action.uri,
                    onAuthSuccess = { requestToken ->
                        viewModelScope.launch {
                            loginUseCase.invoke((requestToken)).fold(
                                onSuccess = {
                                    state = state.copy(isLoggedIn = true)
                                },
                                onError = { error ->
                                    state = state.copy(isLoggedIn = false)
                                }
                            )
                        }
                    }, onAuthFailure = {
                        viewModelScope.launch {
                            sessionStorage.set(null)
                        }
                    }
                )
            }
        }
    }

    private fun handleSchemeTMDBRedirect(
        uri: Uri,
        onAuthSuccess: (String) -> Unit,
        onAuthFailure: () -> Unit,
    ) {
        if (uri.scheme == "movieflix" &&
            uri.host == "auth" &&
            uri.path == "/redirect"
        ) {
            val approved = uri.getQueryParameter("approved")
            val requestToken = uri.getQueryParameter("request_token")

            if (approved == "true" && requestToken != null) {
                onAuthSuccess(requestToken)
            } else {
                onAuthFailure()
            }
        }
    }
}
