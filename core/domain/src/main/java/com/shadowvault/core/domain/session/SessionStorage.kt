package com.shadowvault.core.domain.session

interface SessionStorage {
    suspend fun get(): SessionInfo?
    suspend fun set(info: SessionInfo?)
}