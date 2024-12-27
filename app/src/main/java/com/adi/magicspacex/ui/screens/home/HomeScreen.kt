package com.adi.magicspacex.ui.screens.home

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.adi.magicspacex.R
import com.adi.magicspacex.models.companyInfo.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.ui.screens.home.composables.DragonSection
import com.adi.magicspacex.ui.screens.home.composables.LaunchpadsCarouselSection
import com.adi.magicspacex.ui.screens.home.composables.PastLaunchesCarouselSection
import com.adi.magicspacex.ui.screens.home.composables.RocketsCarouselSection
import com.adi.magicspacex.ui.screens.home.composables.ShipsCarouselSection
import com.adi.magicspacex.ui.screens.home.composables.UpcomingLaunchBanner
import com.adi.magicspacex.utils.composables.FullScreenLoading
import com.adi.magicspacex.utils.composables.VerticalSpacer
import com.adi.magicspacex.utils.extensions.openInExternalBrowser
import com.adi.magicspacex.utils.formatStringToLocalDateString
import com.adi.magicspacex.utils.model.helpers.DataState
import com.adi.magicspacex.utils.model.helpers.State
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import java.util.Date
import java.util.concurrent.TimeUnit

@Composable
fun HomeScreen(
    homeViewState: DataState<HomeViewState>,
    onNavigationToLaunchDetails: (String) -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) {
        when (homeViewState) {
            is State.Idle, State.Loading -> {
                FullScreenLoading()
            }

            is DataState.Loaded -> {
                ScreenContent(
                    homeViewState = homeViewState,
                    onNavigationToLaunchDetails = onNavigationToLaunchDetails,
                )
            }

            is State.Error -> {
                // oh man... RUD happened. Please try to perform next liftoff later
            }
        }
    }
}

@Composable
private fun ScreenContent(
    homeViewState: DataState.Loaded<HomeViewState>,
    onNavigationToLaunchDetails: (launchId: String) -> Unit,
) {
    val scrollState = rememberScrollState()
    val currentScrollPosition = scrollState.value
    val scrollThreshold = 620.dp
    val scrollThresholdInPixels = with(LocalDensity.current) { scrollThreshold.roundToPx() }

    val targetStatusBarColor by remember(currentScrollPosition) {
        derivedStateOf {
            if (currentScrollPosition > scrollThresholdInPixels) {
                Color.Black
            } else {
                Color.Transparent
            }
        }
    }

    val statusBarColor by animateColorAsState(
        targetValue = targetStatusBarColor,
        animationSpec = tween(durationMillis = 1_000, easing = LinearOutSlowInEasing),
        label = "status bar color"
    )

    val context = LocalContext.current
    val view = LocalView.current

    SideEffect {
        val window = (context as Activity).window
        window.statusBarColor = statusBarColor.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        val spacexData = homeViewState.data
        val latestLaunch = spacexData.latestLaunch
        val mockNextLaunchDate = Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(20))

        UpcomingLaunchBanner(
            date = mockNextLaunchDate,
            name = "Polaris Dawn",
        )

        LatestLaunchSection(
            id = latestLaunch.id,
            name = latestLaunch.name,
            date = latestLaunch.launchDate,
            patchUrl = latestLaunch.links.patch.large,
            navigateToLaunchDetails = onNavigationToLaunchDetails
        )

        VerticalSpacer(height = 20.dp)

        ContentSection(
            pastLaunches = spacexData.pastLaunches,
            launchpads = spacexData.launchpads,
            ships = spacexData.ships,
            companyInfo = spacexData.companyInfo,
            rockets = spacexData.rockets,
            dragons = spacexData.dragons,
            navigateToLaunchDetails = onNavigationToLaunchDetails,
        )
    }
}

@Composable
private fun LatestLaunchSection(
    id: String,
    date: String,
    patchUrl: String,
    name: String,
    navigateToLaunchDetails: (launchId: String) -> Unit,
) {
    val saturnComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_saturn)
    )

    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.latest_launch),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleLarge,
                )

                LottieAnimation(
                    modifier = Modifier.size(100.dp),
                    composition = saturnComposition,
                    iterations = LottieConstants.IterateForever,
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(300.dp),
                    model = patchUrl,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                )

                VerticalSpacer(height = 20.dp)

                Text(
                    text = name,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = formatStringToLocalDateString(date),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium,
                )

                VerticalSpacer(height = 20.dp)

                ElevatedButton(
                    shape = RoundedCornerShape(12.dp),
                    onClick = { navigateToLaunchDetails(id) },
                ) {
                    Text(
                        text = "Learn more",
                        style = MaterialTheme.typography.titleSmall,
                    )
                }

                VerticalSpacer(height = 10.dp)
            }
        }
    }
}

@Composable
private fun ContentSection(
    pastLaunches: List<Launch>,
    rockets: List<Rocket>,
    dragons: List<Dragon>,
    launchpads: List<Launchpad>,
    ships: List<Ship>,
    companyInfo: CompanyInfo,
    navigateToLaunchDetails: (String) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        PastLaunchesCarouselSection(
            launches = pastLaunches,
            navigateToLaunchDetails = navigateToLaunchDetails
        )

        VerticalSpacer(height = 20.dp)

        RocketsCarouselSection(rockets = rockets)

        VerticalSpacer(height = 20.dp)

        DragonSection(dragons = dragons)

        VerticalSpacer(height = 20.dp)

        LaunchpadsCarouselSection(launchpads = launchpads)

        VerticalSpacer(height = 20.dp)

        ShipsCarouselSection(ships = ships)

        VerticalSpacer(height = 20.dp)

        AboutSection(
            description = companyInfo.summary,
            websiteUrl = companyInfo.links.website,
        )
    }
}

@Composable
private fun AboutSection(
    description: String,
    websiteUrl: String,
) {
    val context = LocalContext.current
    val astronautComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_astronaut))

    Column {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.titleLarge,
        )

        VerticalSpacer(height = 10.dp)

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Justify),
        )

        VerticalSpacer(height = 5.dp)

        OutlinedButton(
            modifier = Modifier.align(Alignment.End),
            onClick = { context.openInExternalBrowser(url = websiteUrl) },
        ) {
            Text(
                text = stringResource(R.string.see_more),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        VerticalSpacer(height = 20.dp)

        LottieAnimation(
            composition = astronautComposition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.CenterHorizontally),
        )

        VerticalSpacer(height = 20.dp)
    }
}
