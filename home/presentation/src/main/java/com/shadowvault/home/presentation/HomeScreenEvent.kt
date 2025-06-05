package com.shadowvault.home.presentation

import com.shadowvault.core.presentation.ui.ErrorAlertText

sealed interface HomeScreenEvent {
    data class Error(val errorAlertText: ErrorAlertText): HomeScreenEvent
}