package com.shadowvault.auth.domain.account.model

data class CreateRequestTokenResult(
    val success: Boolean,
    val expiresAt: String,
    val requestToken: String,
)
