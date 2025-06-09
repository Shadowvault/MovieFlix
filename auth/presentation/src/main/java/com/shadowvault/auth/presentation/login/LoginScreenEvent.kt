package com.shadowvault.auth.presentation.login

import com.shadowvault.core.presentation.ui.ErrorAlertText

sealed interface LoginScreenEvent {
    data class Error(val errorAlertText: ErrorAlertText) : LoginScreenEvent
    data class OpenBrowser(val token: String) : LoginScreenEvent
    data object LoginSuccess : LoginScreenEvent
}
