package com.shadowvault.core.data.session

import com.shadowvault.core.domain.session.SessionInfo
import kotlinx.serialization.Serializable

@Serializable
data class SessionInfoSerializable(
    val sessionId: String
)

fun SessionInfo.toSessionInfoSerializable(): SessionInfoSerializable {
    return SessionInfoSerializable(
        sessionId = sessionId
    )
}

fun SessionInfoSerializable.SessionInfo(): SessionInfo {
    return SessionInfo(
        sessionId = sessionId
    )
}