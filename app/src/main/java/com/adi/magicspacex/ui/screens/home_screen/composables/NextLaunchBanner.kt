package com.adi.magicspacex.ui.screens.home_screen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.utils.formatStringToLocalDate
import com.adi.magicspacex.utils.launchUrl
import com.adi.magicspacex.utils.showTimeToNextLaunch
import java.util.*

@Composable
fun NextLaunchBanner(nextLaunch: Launch, navigateToLaunchDetails: (String) -> Unit) {
    val context = LocalContext.current
    val isLaunchDateAfterCurrent =
        nextLaunch.date_utc?.let { formatStringToLocalDate(it).after(Calendar.getInstance().time) }

    Surface(color = if (isLaunchDateAfterCurrent != null && isLaunchDateAfterCurrent) Color.LightGray else Color.Red,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable {
                if (isLaunchDateAfterCurrent != null && !isLaunchDateAfterCurrent && nextLaunch.links != null) launchUrl(
                    context, nextLaunch.links.webcast
                ) else if (nextLaunch.id != null) navigateToLaunchDetails(
                    nextLaunch.id
                )
            }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            if (isLaunchDateAfterCurrent != null && isLaunchDateAfterCurrent) {
                NextLaunchWithNotification(nextLaunch = nextLaunch)
            } else if (nextLaunch.name != null) {
                NextLaunchWithWebcast(nextLaunch = nextLaunch)
            }
        }
    }
}

@Composable
private fun NextLaunchWithNotification(nextLaunch: Launch) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Filled.Notifications,
            contentDescription = null,
            tint = Color.Black,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            "Next launch: ", style = MaterialTheme.typography.titleMedium,
        )
        if (nextLaunch.date_utc != null)
            Text(showTimeToNextLaunch(formatStringToLocalDate(nextLaunch.date_utc)))
        if (nextLaunch.name != null)
            Text(nextLaunch.name)
    }
    Icon(
        Icons.Filled.ArrowForward, contentDescription = null, tint = Color.Black
    )
}

@Composable
private fun NextLaunchWithWebcast(nextLaunch: Launch) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            "LIVE: ", style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White
            )
        )
        Text(
            nextLaunch.name!!, style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White
            )
        )
    }
    Icon(
        Icons.Filled.PlayArrow, contentDescription = null, tint = Color.White
    )
}