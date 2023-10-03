package com.adi.magicspacex.ui.screens.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.utils.extensions.openInExternalBrowser
import com.adi.magicspacex.utils.formatStringToLocalDate
import com.adi.magicspacex.utils.showTimeToNextLaunch
import java.util.Calendar

@Composable
fun NextLaunchBanner(nextLaunch: Launch, navigateToLaunchDetails: (String) -> Unit) {
    val context = LocalContext.current
    val isLaunchDateAfterCurrent =
        nextLaunch.date_utc?.let { formatStringToLocalDate(it).after(Calendar.getInstance().time) }

    Surface(
        color = if (isLaunchDateAfterCurrent != null && isLaunchDateAfterCurrent) {
            MaterialTheme.colorScheme.tertiary
        } else {
            MaterialTheme.colorScheme.secondary
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(bottom = 20.dp)
            .clickable {
                if (isLaunchDateAfterCurrent != null && !isLaunchDateAfterCurrent && nextLaunch.links != null) {
                    context.openInExternalBrowser(url = nextLaunch.links.webcast)
                } else if (nextLaunch.id != null) {
                    navigateToLaunchDetails(nextLaunch.id)
                }
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            if (isLaunchDateAfterCurrent != null && isLaunchDateAfterCurrent) {
                NextLaunchWithNotification(nextLaunch = nextLaunch)
            } else if (nextLaunch.name != null) {
                Text(
                    text = nextLaunch.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                )

                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Composable
private fun NextLaunchWithNotification(nextLaunch: Launch) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Notifications,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )

        Spacer(modifier = Modifier.width(10.dp))

        if (nextLaunch.date_utc != null) {
            Text(text = showTimeToNextLaunch(formatStringToLocalDate(nextLaunch.date_utc)))
        }

        if (nextLaunch.name != null) {
            Text(text = nextLaunch.name)
        }
    }

    Icon(
        imageVector = Icons.Filled.ArrowForward,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onPrimary,
    )
}