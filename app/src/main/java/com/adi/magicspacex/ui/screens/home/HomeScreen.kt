package com.adi.magicspacex.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.repository.SpacexData
import com.adi.magicspacex.ui.screens.home.composables.DragonColumn
import com.adi.magicspacex.ui.screens.home.composables.LaunchpadsCarouselSection
import com.adi.magicspacex.ui.screens.home.composables.NextLaunchBanner
import com.adi.magicspacex.ui.screens.home.composables.PastLaunchesCarouselSection
import com.adi.magicspacex.ui.screens.home.composables.RocketsCarouselSection
import com.adi.magicspacex.ui.screens.home.composables.ShipsCarouselSection
import com.adi.magicspacex.utils.composables.LoadingSection
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
    homeViewState: DataState<SpacexData?>,
    navigateToLaunchDetails: (String) -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
    ) {
        HomeScreenBody(
            homeViewState = homeViewState,
            navigateToLaunchDetails = navigateToLaunchDetails,
        )
    }
}

@Composable
private fun HomeScreenBody(
    homeViewState: DataState<SpacexData?>,
    navigateToLaunchDetails: (String) -> Unit,
) {
    LoadingSection(isLoading = homeViewState is State.Loading) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            if (homeViewState is DataState.Loaded && homeViewState.data != null) {
                val spacexData = homeViewState.data

                if (spacexData.nextLaunch != null) {
                    //TODO: redesign the banner, use AnimatedVisibility if available
                    NextLaunchBanner(spacexData.nextLaunch, navigateToLaunchDetails)
                }

                if (spacexData.latestLaunch != null) {
                    LatestLaunchSection(spacexData.latestLaunch, navigateToLaunchDetails)

                    VerticalSpacer(height = 20.dp)
                }

                ContentSection(
                    spacexData = spacexData,
                    navigateToLaunchDetails = navigateToLaunchDetails,
                )
            }
        }
    }
}

@Composable
private fun LatestLaunchSection(
    launch: Launch,
    navigateToLaunchDetails: (String) -> Unit,
) {
    val saturnComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_saturn))

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
        if ((launch.links != null) && launch.links.patch.large.isNotEmpty()) {
            AsyncImage(
                modifier = Modifier.size(300.dp),
                model = launch.links.patch.large,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
        }

        VerticalSpacer(height = 20.dp)

        if (launch.name != null) {
            Text(
                text = launch.name,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        VerticalSpacer(height = 20.dp)

        ElevatedButton(
            shape = RoundedCornerShape(12.dp),
            onClick = { /*TODO*/ }) {
            Text(
                text = "See more",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun ContentSection(
    spacexData: SpacexData,
    navigateToLaunchDetails: (String) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        PastLaunchesCarouselSection(
            launches = spacexData.pastLaunches,
            navigateToLaunchDetails = navigateToLaunchDetails
        )

        VerticalSpacer(height = 20.dp)

        RocketsCarouselSection(rockets = spacexData.rockets)

        DragonColumn(dragons = spacexData.dragons)

        LaunchpadsCarouselSection(launchpads = spacexData.launchpads)

        ShipsCarouselSection(ships = spacexData.ships)

        if (spacexData.companyInfo != null) {
            VerticalSpacer(height = 20.dp)

            AboutSection(companyInfo = spacexData.companyInfo)
        }
    }
}

@Composable
fun AboutSection(companyInfo: CompanyInfo) {
    val context = LocalContext.current
    val astronautComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_astronaut))

    Column {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.titleLarge,
        )

        VerticalSpacer(height = 20.dp)

        Text(
            text = companyInfo.summary,
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Justify),
        )

        VerticalSpacer(height = 20.dp)

        OutlinedButton(
            onClick = {
                context.openInExternalBrowser(url = companyInfo.links.website)
            },
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
