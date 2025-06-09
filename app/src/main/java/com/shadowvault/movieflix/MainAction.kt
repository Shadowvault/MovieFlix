package com.shadowvault.movieflix

import android.net.Uri

sealed interface MainAction {
    data class OnHandleDeepLink(val uri: Uri?) : MainAction
}
