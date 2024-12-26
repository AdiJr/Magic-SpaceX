package com.adi.magicspacex.ui.screens.home.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adi.magicspacex.utils.composables.VerticalSpacer
import com.adi.magicspacex.utils.extensions.openInExternalBrowser
import com.adi.magicspacex.utils.formatStringToLocalDate
import kotlinx.coroutines.delay
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * A composable function that displays a countdown timer.
 *
 * The timer counts down from the specified [date] to zero, updating the displayed
 * time every second. When the timer reaches zero, the [onTimerEnd] callback is invoked.
 *
 * @param date The date to count from.
 * @param onTimerEnd An optional callback function that is invoked when the timer reaches zero.
 */
@Composable
fun CountdownTimer(
    date: Date,
    onTimerEnd: () -> Unit = {},
) {
    val initialDifferenceMillis = date.time - System.currentTimeMillis()
    var remainingTimeMillis by remember { mutableStateOf(initialDifferenceMillis) }

    val remainingTime by remember(remainingTimeMillis) {
        derivedStateOf {
            if (remainingTimeMillis <= 0L) {
                RemainingTime(
                    days = 0L,
                    hours = 0L,
                    minutes = 0L,
                    seconds = 0L,
                )
            } else {
                val days = TimeUnit.MILLISECONDS.toDays(remainingTimeMillis)
                val remainingHoursMillis = remainingTimeMillis % TimeUnit.DAYS.toMillis(1)
                val hours = TimeUnit.MILLISECONDS.toHours(remainingHoursMillis)
                val remainingMinutesMillis = remainingHoursMillis % TimeUnit.HOURS.toMillis(1)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMinutesMillis)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingMinutesMillis) % 60

                RemainingTime(
                    days = days,
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds,
                )
            }
        }
    }

    LaunchedEffect(remainingTimeMillis) {
        if (remainingTimeMillis >= -3_000) {
            delay(1000)
            remainingTimeMillis -= 1000
        }
    }

    LaunchedEffect(remainingTime) {
        if (remainingTime.seconds == 0L) {
            onTimerEnd()
        }
    }

    val isBelowTenSeconds = remainingTime.seconds <= 10

    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (remainingTime.seconds in 1..10) 0f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = EaseInOut
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha animation",
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AnimatedVisibility(isBelowTenSeconds.not()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TimeComponent(
                        label = "DAYS",
                        value = remainingTime.days,
                    )

                    TimeComponent(
                        label = "HOURS",
                        value = remainingTime.hours,
                    )

                    TimeComponent(
                        label = "MINUTES",
                        value = remainingTime.minutes,
                    )
                }
            }

            if (remainingTime.seconds > 0) {
                TimeComponent(
                    label = "SECONDS",
                    value = remainingTime.seconds,
                    alpha = alpha,
                )
            }
        }

        AnimatedVisibility(visible = remainingTime.seconds == 0L && remainingTimeMillis >= -2_500) {
            VerticalSpacer(height = 10.dp)

            Text(
                text = "LIFTOFF!",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        AnimatedVisibility(visible = remainingTime.seconds <= 8) {
            val context = LocalContext.current

            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                onClick = { context.openInExternalBrowser(url = "https://x.com/SpaceX/status/1833358277805039800") },
            ) {
                Text(
                    text = "Watch the mission live",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Composable
private fun TimeComponent(
    label: String,
    value: Long,
    alpha: Float = 1f,
) {
    Column(
        modifier = Modifier.size(90.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.DarkGray)
                .size(60.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(alpha),
                text = String.format(Locale.getDefault(), "%02d", value),
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        VerticalSpacer(height = 5.dp)

        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

private data class RemainingTime(
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long,
)

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun TimeComponentWithDoubleDigitsPreview() {
    TimeComponent(
        label = "MINUTES",
        alpha = 1f,
        value = 12,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun TimeComponentWithSingleDigitsPreview() {
    TimeComponent(
        label = "MINUTES",
        alpha = 1f,
        value = 8,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun CountdownTimerPreview() {
    formatStringToLocalDate("2025-10-05T16:00:00.000Z")?.let { CountdownTimer(date = it) }
}