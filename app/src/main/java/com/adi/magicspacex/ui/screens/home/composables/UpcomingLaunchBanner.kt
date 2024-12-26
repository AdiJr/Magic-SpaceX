package com.adi.magicspacex.ui.screens.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adi.magicspacex.utils.composables.VerticalSpacer
import com.adi.magicspacex.utils.extensions.openInExternalBrowser
import com.adi.magicspacex.utils.theme.LightDarkPreview
import java.util.Date
import java.util.concurrent.TimeUnit

@Composable
fun UpcomingLaunchBanner(
    date: Date,
    name: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp)
    ) {
        val exampleMissionImageBackgroundUrl =
            "https://pbs.twimg.com/media/GXJpz8FbkAAh5Lp.jpg:large"

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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 10.dp),
                text = "Next mission",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var showCountdownHeadline by remember { mutableStateOf(true) }

            Text(
                text = name,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )

            VerticalSpacer(height = 20.dp)

            if (showCountdownHeadline) {
                Text(
                    text = "Time to launch",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                )

                VerticalSpacer(height = 10.dp)
            }

            CountdownTimer(
                date = date,
                onTimerEnd = { showCountdownHeadline = false },
            )

            VerticalSpacer(height = 10.dp)

            val context = LocalContext.current

            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                onClick = { context.openInExternalBrowser(url = "https://polarisprogram.com/dawn/") },
            ) {
                Text(
                    text = "Learn more",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Composable
@LightDarkPreview
private fun UpcomingLaunchSectionPreview() {
    UpcomingLaunchBanner(
        date = Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(20)),
        name = "Astro-Bot",
    )
}