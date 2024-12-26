package com.adi.magicspacex.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

/**
 * Formats a UTC date string into a localized date and time string.
 *
 * This function takes a date string in UTC format, parses it, and then formats it
 * into a localized string representation based on the device's default locale and time zone.
 *
 * @param utcDate The date string in UTC format (e.g., "2023-12-25T14:30:00Z").
 * @return A localized date and time string (e. g., "Monday, 25.12.2023 - 14:30"), or an empty string if the input is invalid.
 * @throws IllegalArgumentException if the input string is not in a valid UTC format.
 */
fun formatStringToLocalDateString(utcDate: String): String {
    val date = formatStringToLocalDate(utcDate)

    if (date != null) {
        val outputFormat = SimpleDateFormat("EEEE, dd.MM.yyyy - HH:mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()

        return outputFormat.format(date)
    } else {
        return ""
    }
}

fun formatStringToLocalDate(utcDate: String): Date? {
    val inputFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    return inputFormat.parse(utcDate)
}

/**
 * Calculates the time difference between the current time and this [Date] object,
 * and returns a formatted string representing the time until the next launch.
 *
 * The output format is "(Xd : Yh : Zm)", where:
 * - X is the number of days.
 * - Y is the number of hours (remaining after calculating days).
 * - Z is the number of minutes (remaining after calculating hours).
 *
 * If the date is in the past, it will return a string with negative values.
 *
 * @return A formatted string representing the time until the next launch, or a string with negative values if the date is in the past.
 */
fun Date.timeToNextLaunch(): String {
    val differenceMillis = this.time - System.currentTimeMillis()

    val elapsedDays = TimeUnit.MILLISECONDS.toDays(differenceMillis)
    val remainingHoursMillis = differenceMillis % TimeUnit.DAYS.toMillis(1)
    val elapsedHours = TimeUnit.MILLISECONDS.toHours(remainingHoursMillis)
    val remainingMinutesMillis = remainingHoursMillis % TimeUnit.HOURS.toMillis(1)
    val elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(remainingMinutesMillis)

    return "(${elapsedDays}d : ${elapsedHours}h : ${elapsedMinutes}m)"
}
