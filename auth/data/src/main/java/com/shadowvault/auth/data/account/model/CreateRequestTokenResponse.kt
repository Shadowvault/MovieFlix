package com.shadowvault.auth.data.account.model

import com.shadowvault.auth.domain.account.model.CreateRequestTokenResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRequestTokenResponse(
    val success: Boolean,
    @SerialName("expires_at") val expiresAt: String,
    @SerialName("request_token") val requestToken: String,
)

fun CreateRequestTokenResponse.toCreateRequestTokenResult(): CreateRequestTokenResult {
    return CreateRequestTokenResult(
        success = this.success,
        expiresAt = this.expiresAt,
        requestToken = this.requestToken
    )
}

fun CreateRequestTokenResult.toCreateRequestTokenResponse(): CreateRequestTokenResponse {
    return CreateRequestTokenResponse(
        success = this.success,
        expiresAt = this.expiresAt,
        requestToken = this.requestToken
    )
}
