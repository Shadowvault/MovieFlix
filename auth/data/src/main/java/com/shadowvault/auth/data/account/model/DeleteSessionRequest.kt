package com.shadowvault.auth.data.account.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteSessionRequest(@SerialName("session_id") val sessionId: String)
