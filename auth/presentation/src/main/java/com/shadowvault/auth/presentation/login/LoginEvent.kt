package com.shadowvault.auth.presentation.login

import com.shadowvault.core.presentation.ui.ErrorAlertText

sealed interface LoginEvent {
    data class Error(val errorAlertText: ErrorAlertText): LoginEvent
    data class OpenBrowser(val token: String): LoginEvent
    data object LoginSuccess: LoginEvent
}