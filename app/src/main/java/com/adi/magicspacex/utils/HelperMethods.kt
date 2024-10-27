package com.adi.magicspacex.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatStringToLocalDate(utcDate: String): Date {
    val inputFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    return inputFormat.parse(utcDate)
}

fun formatStringToLocalDateString(utcDate: String): String {
    val date = formatStringToLocalDate(utcDate)

    val outputFormat = SimpleDateFormat("EEEE, dd.MM.yyyy HH:mm", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getDefault()

    return outputFormat.format(date)
}

fun Date.timeToNextLaunch(): String {
    var difference: Long = this.time - System.currentTimeMillis()

    val secondsInMilli: Long = 1000
    val minutesInMilli: Long = secondsInMilli * 60
    val hoursInMilli: Long = minutesInMilli * 60
    val daysInMilli: Long = hoursInMilli * 24

    val elapsedDays: Long = difference / daysInMilli
    val elapsedHours: Long = difference / hoursInMilli

    difference %= hoursInMilli

    val elapsedMinutes: Long = difference / minutesInMilli
    difference %= minutesInMilli

    return "(${elapsedDays}d : ${elapsedHours}h : ${elapsedMinutes}m)"
}
