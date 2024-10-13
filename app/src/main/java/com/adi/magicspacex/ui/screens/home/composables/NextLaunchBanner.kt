package com.adi.magicspacex.ui.screens.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.utils.extensions.openInExternalBrowser
import com.adi.magicspacex.utils.formatStringToLocalDate
import com.adi.magicspacex.utils.theme.LightDarkPreview
import com.adi.magicspacex.utils.timeToNextLaunch
import java.util.Calendar

@Composable
fun UpcomingLaunchSection(
    id: String,
    webcastUrl: String,
    date: String,
    name: String,
    navigateToLaunchDetails: (String) -> Unit,
) {
    val context = LocalContext.current
    val isLaunchDateAfterCurrent =
        formatStringToLocalDate(date).after(Calendar.getInstance().time)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(all = 20.dp)
            .clickable {
                if (isLaunchDateAfterCurrent.not()) {
                    context.openInExternalBrowser(url = webcastUrl)
                } else {
                    navigateToLaunchDetails(id)
                }
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
        )
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
            val date = formatStringToLocalDate(nextLaunch.date_utc)
            Text(text = date.timeToNextLaunch())
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

@Composable
@LightDarkPreview
private fun UpcomingLaunchSectionPreview() {
    UpcomingLaunchSection(
        id = "id",
        webcastUrl = "",
        date = "",
        name = "xcxc",
        navigateToLaunchDetails = {},
    )
}