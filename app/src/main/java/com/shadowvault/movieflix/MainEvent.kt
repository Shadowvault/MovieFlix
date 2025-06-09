package com.shadowvault.movieflix

sealed interface MainEvent {
    data object LoginSuccess : MainEvent
}
