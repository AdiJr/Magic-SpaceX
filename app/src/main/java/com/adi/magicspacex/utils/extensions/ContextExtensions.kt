package com.adi.magicspacex.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import timber.log.Timber

/**
 * Starts an external browser to load the given [url].
 */
fun Context.openInExternalBrowser(url: String, onActivityNotFound: () -> Unit = {}) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    } catch (ex: Exception) {
        Timber.e(ex, "No activity can fulfil the request - external browser.")
        onActivityNotFound()
    }
}