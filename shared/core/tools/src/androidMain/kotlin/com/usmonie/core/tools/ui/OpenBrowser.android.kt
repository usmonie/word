package com.usmonie.core.tools.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun OpenBrowser(url: Url) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.link))
    context.startActivity(intent)
}
