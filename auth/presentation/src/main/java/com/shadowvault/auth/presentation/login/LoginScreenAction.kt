package com.shadowvault.auth.presentation.login

sealed interface LoginScreenAction {
    data object OnLoginButtonPress : LoginScreenAction
}
