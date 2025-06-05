package com.shadowvault.auth.data.account.model

import com.shadowvault.auth.domain.account.model.CreateSessionResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionResponse(
    val success: Boolean,
    @SerialName("session_id") val sessionId: String,
)

fun CreateSessionResponse.toCreateSessionResult(): CreateSessionResult {
    return CreateSessionResult(success, sessionId)
}

fun CreateSessionResult.toCreateSessionResponse(): CreateSessionResponse {
    return CreateSessionResponse(success, sessionId)
}
