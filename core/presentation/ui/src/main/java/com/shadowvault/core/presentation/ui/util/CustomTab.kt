package com.shadowvault.core.presentation.ui.util

import android.content.Context
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

fun launchCustomTab(
  context: Context,
  url: String,
) {
  val packageName = CustomTabsClient.getPackageName(context, null)

  val customTabsIntent = CustomTabsIntent.Builder()
    .setColorScheme(CustomTabsIntent.COLOR_SCHEME_SYSTEM)
    .setShowTitle(true)
    .build()

  if (packageName != null) {
    customTabsIntent.intent.setPackage(packageName)
  }

  customTabsIntent.launchUrl(context, url.toUri())
}
