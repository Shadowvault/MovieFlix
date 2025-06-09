package com.shadowvault.core.presentation.ui

import com.shadowvault.core.domain.util.DataError

data class ErrorAlertText(
    val title: UiText,
    val description: UiText
)

fun DataError.toAlertText(): ErrorAlertText {
    return ErrorAlertText(
        title = UiText.DynamicString("title"),
        description = UiText.DynamicString("desc")
    )
//    return when (this) {
//        DataError.Local.DISK_FULL -> TODO()
//        DataError.Local.PERMISSION_DENIED -> TODO()
//        DataError.Local.FILE_NOT_FOUND -> TODO()
//        DataError.Local.CACHE_FAILURE -> TODO()
//        DataError.Local.DATABASE_FAILURE -> TODO()
//        DataError.Network.NO_NETWORK -> TODO()
//        DataError.Network.NO_INTERNET -> TODO()
//        DataError.Network.SERVER_REQUEST_TIMEOUT -> TODO()
//        DataError.Network.CLIENT_REQUEST_TIMEOUT -> TODO()
//        DataError.Network.CONNECTION_TIMEOUT -> TODO()
//        DataError.Network.SOCKET_TIMEOUT -> TODO()
//        DataError.Network.SERVER_UNREACHABLE -> TODO()
//        DataError.Network.SSL_ERROR -> TODO()
//        DataError.Network.UNKNOWN_HOST -> TODO()
//        DataError.Network.SERIALIZATION -> TODO()
//        DataError.Network.UNKNOWN -> TODO()
//        is DataError.Remote.Custom -> TODO()
//        DataError.Remote.Http.UNAUTHORIZED -> TODO()
//        DataError.Remote.Http.FORBIDDEN -> TODO()
//        DataError.Remote.Http.NOT_FOUND -> TODO()
//        DataError.Remote.Http.CONFLICT -> TODO()
//        DataError.Remote.Http.TOO_MANY_REQUESTS -> TODO()
//        DataError.Remote.Http.PAYLOAD_TOO_LARGE -> TODO()
//        DataError.Remote.Http.SERVER_ERROR -> TODO()
//        DataError.Remote.Http.BAD_REQUEST -> TODO()
//    }
}
