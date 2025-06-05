package com.shadowvault.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadowvault.auth.domain.account.AccountRepository
import com.shadowvault.core.domain.util.fold
import com.shadowvault.core.presentation.ui.toAlertText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.onStart {

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LoginScreenState())


    private val eventChannel = Channel<LoginScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginScreenAction) {
        when (action) {
            LoginScreenAction.OnLoginButtonPress -> createRequestToken()
            else -> Unit
        }
    }

    private fun createRequestToken() {
        viewModelScope.launch {
            _state.update { state -> state.copy(isLoading = true) }
            accountRepository.createRequestToken().fold(
                onSuccess = { data ->
                    eventChannel.send(
                        LoginScreenEvent.OpenBrowser(
                            createLoginUrl(data.requestToken)
                        )
                    )
                },
                onError = { error ->
                    eventChannel.send(
                        LoginScreenEvent.Error(
                            error.toAlertText()
                        )
                    )
                }
            )
            _state.update { state -> state.copy(isLoading = false) }
        }
    }

    private fun createLoginUrl(token: String): String {
        val redirectUrl = "movieflix://auth/redirect"
        return "https://www.themoviedb.org/authenticate/$token?redirect_to=$redirectUrl"
    }
}