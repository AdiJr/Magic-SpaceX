package com.adi.magicspacex.utils

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

fun formatStringToLocalDate(utcDate: String): Date {
    val inputFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("EEEE, dd.MM.yyyy HH:mm", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getDefault()

    return inputFormat.parse(utcDate)!!
}

fun formatStringToLocalDateString(utcDate: String): String {
    val inputFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("EEEE, dd.MM.yyyy HH:mm", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getDefault()

    val date: Date? = inputFormat.parse(utcDate)

    return outputFormat.format(date!!)
}

fun showTimeToNextLaunch(launchDate: Date): String {
    var difference: Long = launchDate.time - Calendar.getInstance().time.time

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays: Long = difference / daysInMilli

    val elapsedHours: Long = difference / hoursInMilli
    difference %= hoursInMilli

    val elapsedMinutes: Long = difference / minutesInMilli
    difference %= minutesInMilli

    return "(${elapsedDays}d : ${elapsedHours}h : ${elapsedMinutes}m) "
}