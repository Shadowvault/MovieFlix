package com.shadowvault.core.domain.util

import java.io.IOException

sealed interface DataError : Error {

    // 🌐 Network and transport-related issues
    enum class Network : DataError {
        NO_NETWORK,
        NO_INTERNET,             // No connection available
        SERVER_REQUEST_TIMEOUT,  // Request took too long
        CLIENT_REQUEST_TIMEOUT,   // HttpRequestTimeoutException - full request duration exceeded
        CONNECTION_TIMEOUT,       // ConnectTimeoutException - can't reach server
        SOCKET_TIMEOUT,
        SERVER_UNREACHABLE,      // Host unreachable or DNS failure
        SSL_ERROR,               // Certificate issue
        UNKNOWN_HOST,            // Could not resolve host
        SERIALIZATION,           // JSON parsing failed
        UNKNOWN                  // Anything uncategorized
    }

    // 🧱 Local (device/storage/permissions) issues
    enum class Local : DataError {
        DISK_FULL,               // No storage left
        PERMISSION_DENIED,       // Missing storage or network permissions
        FILE_NOT_FOUND,          // Missing file locally
        CACHE_FAILURE,           // Failed to read/write cache
        DATABASE_FAILURE         // SQLite or Room errors
    }

    // 🛰️ Server responded with structured error
    sealed interface Remote : DataError {
        enum class Http : Remote {
            UNAUTHORIZED,           // 401
            FORBIDDEN,              // 403
            NOT_FOUND,              // 404
            CONFLICT,               // 409
            TOO_MANY_REQUESTS,      // 429
            PAYLOAD_TOO_LARGE,      // 413
            SERVER_ERROR,           // 5xx
            BAD_REQUEST             // 400
        }

        data class Custom(
            val code: String,       // Custom backend code like "USER_NOT_FOUND"
            val message: String     // Human-readable message
        ) : Remote
    }
}

fun DataError.toThrowable(): Throwable = IOException("Paging error: $this")


