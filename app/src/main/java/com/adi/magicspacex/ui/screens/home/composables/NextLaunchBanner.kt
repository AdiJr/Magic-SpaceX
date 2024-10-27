package com.adi.magicspacex.ui.screens.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adi.magicspacex.utils.composables.VerticalSpacer
import com.adi.magicspacex.utils.formatStringToLocalDateString
import com.adi.magicspacex.utils.theme.LightDarkPreview

@Composable
fun Header(
    date: String,
    name: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
    ) {
        val exampleMissionImageBackgroundUrl =
            "https://cdn.mos.cms.futurecdn.net/XzyrtUePiZtmBgbfHQCFtc.jpg"

        // consider using pager to nicely fade in and out 5 photos in random order
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = exampleMissionImageBackgroundUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        0.0f to Color.Transparent,
                        0.7f to Color.Black.copy(alpha = 0.6f),
                        1.0f to Color.Black.copy(alpha = 0.8f)
                    )
                ),
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = name,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = formatStringToLocalDateString(date),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
            )

            VerticalSpacer(15.dp)
        }
    }
}

@Composable
@LightDarkPreview
private fun UpcomingLaunchSectionPreview() {
    Header(
        date = "",
        name = "xcxc",
    )
}