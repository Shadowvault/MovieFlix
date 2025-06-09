package com.shadowvault.auth.data.account.model

import com.shadowvault.auth.domain.account.model.CreateSessionParams
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionRequest(@SerialName("request_token") val requestToken: String)

fun CreateSessionRequest.toCreateSessionParams(): CreateSessionParams {
    return CreateSessionParams(requestToken)
}

fun CreateSessionParams.toCreateSessionRequest(): CreateSessionRequest {
    return CreateSessionRequest(requestToken)
}
