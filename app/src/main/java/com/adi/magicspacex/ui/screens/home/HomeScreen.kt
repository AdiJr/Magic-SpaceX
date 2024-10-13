package com.adi.magicspacex.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import com.adi.magicspacex.ui.screens.home.composables.UpcomingLaunchSection
import com.adi.magicspacex.utils.composables.VerticalSpacer
import com.adi.magicspacex.utils.extensions.openInExternalBrowser
import com.adi.magicspacex.utils.model.helpers.DataState
import com.adi.magicspacex.utils.model.helpers.State
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun HomeScreen(
    homeViewState: DataState<HomeViewState>,
    navigateToLaunchDetails: (String) -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.systemBarsPadding().fillMaxSize()
    ) {
        HomeScreenBody(
            homeViewState = homeViewState,
            navigateToLaunchDetails = navigateToLaunchDetails,
        )
    }
}

@Composable
private fun HomeScreenBody(
    homeViewState: DataState<HomeViewState>,
    navigateToLaunchDetails: (String) -> Unit,
) {
    when (homeViewState) {
        is State.Idle, State.Loading -> {
            Box {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .requiredSize(100.dp)
                        .padding(30.dp)
                )
            }
        }

        is DataState.Loaded -> {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                val spacexData = homeViewState.data
                val latestLaunch = spacexData.latestLaunch
                val nextLaunch = spacexData.nextLaunch

                if (nextLaunch.id != null &&
                    nextLaunch.name != null &&
                    nextLaunch.date_utc != null &&
                    nextLaunch.links != null
                ) {
                    UpcomingLaunchSection(
                        id = nextLaunch.id,
                        date = nextLaunch.date_utc,
                        name = nextLaunch.name,
                        webcastUrl = nextLaunch.links.webcast,
                        navigateToLaunchDetails = navigateToLaunchDetails
                    )
                }

                if (latestLaunch.id != null &&
                    latestLaunch.name != null &&
                    latestLaunch.links != null
                ) {
                    LatestLaunchSection(
                        id = latestLaunch.id,
                        name = latestLaunch.name,
                        patchUrl = latestLaunch.links.patch.large,
                        navigateToLaunchDetails = navigateToLaunchDetails
                    )

                    VerticalSpacer(height = 20.dp)
                }

                ContentSection(
                    pastLaunches = spacexData.pastLaunches,
                    launchpads = spacexData.launchpads,
                    ships = spacexData.ships,
                    companyInfo = spacexData.companyInfo,
                    rockets = spacexData.rockets,
                    dragons = spacexData.dragons,
                    navigateToLaunchDetails = navigateToLaunchDetails,
                )
            }
        }

        is State.Error -> {
            // oh man... RUD happened. Please try to perform next liftoff later
        }
    }
}

@Composable
private fun LatestLaunchSection(
    id: String,
    patchUrl: String,
    name: String,
    navigateToLaunchDetails: (String) -> Unit,
) {
    val saturnComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_saturn)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.latest_launch),
            style = MaterialTheme.typography.titleLarge,
        )

        LottieAnimation(
            composition = saturnComposition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(100.dp),
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
            style = MaterialTheme.typography.titleLarge,
        )

        VerticalSpacer(height = 20.dp)

        ElevatedButton(
            shape = RoundedCornerShape(12.dp),
            onClick = { navigateToLaunchDetails(id) },
        ) {
            Text(
                text = "Learn more",
                style = MaterialTheme.typography.bodyMedium,
            )
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

        VerticalSpacer(height = 20.dp)

        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Justify),
        )

        VerticalSpacer(height = 20.dp)

        OutlinedButton(
            onClick = { context.openInExternalBrowser(url = websiteUrl) },
        ) {
            Text(
                text = stringResource(R.string.see_more),
                style = MaterialTheme.typography.titleMedium,
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
