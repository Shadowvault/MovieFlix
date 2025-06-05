package com.shadowvault.auth.presentation.login

sealed interface LoginAction {
    data object OnLoginButtonPress: LoginAction
}