package com.shadowvault.core.presentation.ui.util

import android.content.Context
import android.content.Intent

fun shareUrl(context: Context, url: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "Share URL via")
    context.startActivity(shareIntent)
}
