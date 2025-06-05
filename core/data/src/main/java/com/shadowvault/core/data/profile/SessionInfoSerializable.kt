package com.shadowvault.core.data.profile

import com.shadowvault.core.domain.profile.ProfileInfo
import com.shadowvault.core.domain.session.SessionInfo
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfoSerializable(
    val name: String,
    val username: String,
    val id: Int,
    val avatar: String
)

fun ProfileInfo.toProfileInfoSerializable(): ProfileInfoSerializable {
    return ProfileInfoSerializable(name, username, id, avatar)
}

fun ProfileInfoSerializable.toProfileInfo(): ProfileInfo {
    return ProfileInfo(name, username, id, avatar)
}