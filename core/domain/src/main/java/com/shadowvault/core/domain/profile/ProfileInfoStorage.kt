package com.shadowvault.core.domain.profile

interface ProfileInfoStorage {
    suspend fun get(): ProfileInfo?
    suspend fun set(profileInfo: ProfileInfo?)
}
