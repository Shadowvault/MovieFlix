package com.shadowvault.auth.domain.account.model

data class CreateSessionResult(
    val success: Boolean,
    val sessionId: String,
)
