package com.shadowvault.movie_details.presentation

import com.shadowvault.core.presentation.ui.ErrorAlertText

sealed interface MovieDetailsScreenEvent {
    data class Error(val errorAlertText: ErrorAlertText): MovieDetailsScreenEvent
}