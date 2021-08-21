package com.adi.magicspacex.utils.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*

fun launchUrl(context: Context, url: String) {
    val browserIntent =
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browserIntent)
}

fun formatStringToLocalDate(utcDate: String): String {
    val inputFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("EEEE, dd.MM.yyyy HH:mm", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getDefault()

    val date: Date? = inputFormat.parse(utcDate)

    return outputFormat.format(date!!)
}