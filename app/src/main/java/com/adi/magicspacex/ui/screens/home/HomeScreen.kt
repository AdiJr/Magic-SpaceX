package com.adi.magicspacex.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adi.magicspacex.R
import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.ui.screens.home.composables.DragonColumn
import com.adi.magicspacex.ui.screens.home.composables.LaunchpadsCarouselSection
import com.adi.magicspacex.ui.screens.home.composables.NextLaunchBanner
import com.adi.magicspacex.ui.screens.home.composables.PastLaunchesCarouselSection
import com.adi.magicspacex.ui.screens.home.composables.RocketsCarouselSection
import com.adi.magicspacex.ui.screens.home.composables.ShipsCarouselSection
import com.adi.magicspacex.utils.constants.FLICKR_LOGO_URL
import com.adi.magicspacex.utils.constants.TWITTER_LOGO_URL
import com.adi.magicspacex.utils.constants.WEBSITE_LOGO_URL
import com.adi.magicspacex.utils.extensions.openInExternalBrowser
import com.adi.magicspacex.utils.ui.LoadingSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewState: HomeViewState,
    navigateToLaunchDetails: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                modifier = Modifier.height(45.dp)
            )
        },
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            HomeScreenBody(
                homeViewState = homeViewState,
                navigateToLaunchDetails = navigateToLaunchDetails,
            )
        }
    }
}

@Composable
private fun HomeScreenBody(
    homeViewState: HomeViewState,
    navigateToLaunchDetails: (String) -> Unit,
) {
    LoadingSection(isLoading = homeViewState.isLoading) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
        ) {
            if (homeViewState.nextLaunch != null) {
                NextLaunchBanner(homeViewState.nextLaunch, navigateToLaunchDetails)
            }

            if (homeViewState.latestLaunch != null) {
                LatestLaunchSection(homeViewState.latestLaunch, navigateToLaunchDetails)
            }

            homeViewState.companyInfo?.let {
                ContentSection(
                    homeViewState.rockets,
                    homeViewState.pastLaunches,
                    homeViewState.dragons,
                    homeViewState.launchpads,
                    homeViewState.ships,
                    it,
                    navigateToLaunchDetails,
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
    Box(modifier = Modifier.clickable {
        if (launch.id != null) {
            navigateToLaunchDetails(launch.id)
        }
    }) {
        if ((launch.links != null) && launch.links.patch.large.isNotEmpty()) {
            AsyncImage(
                model = launch.links.patch.large,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(400.dp)
            )
        }

        if (launch.links != null && launch.links.patch.large.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(400.dp)
                    .align(Alignment.Center)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent, Color.Black.copy(alpha = 0.9f)
                            ), 0.0f, Float.POSITIVE_INFINITY
                        )
                    )
            )
        }
        if (launch.name != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.latest_launch),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = launch.name,
                    style = MaterialTheme.typography.titleLarge,
                )

            }
        }
    }
}

@Composable
private fun ContentSection(
    rockets: List<Rocket>,
    pastLaunches: List<Launch>,
    dragons: List<Dragon>,
    launchpads: List<Launchpad>,
    ships: List<Ship>,
    companyInfo: CompanyInfo,
    navigateToLaunchDetails: (String) -> Unit,
) {

    Column(Modifier.padding(horizontal = 20.dp)) {
        PastLaunchesCarouselSection(pastLaunches, navigateToLaunchDetails)
        RocketsCarouselSection(rockets)
        DragonColumn(dragons)
        LaunchpadsCarouselSection(launchpads)
        ShipsCarouselSection(ships)
        AboutSection(companyInfo)
    }
}

@Composable
fun AboutSection(companyInfo: CompanyInfo) {
    val context = LocalContext.current
    Text(
        text = stringResource(R.string.about),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 20.dp)
    )
    Text(
        text = companyInfo.summary,
        style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Justify),
    )
    OutlinedButton(
        onClick = { context.openInExternalBrowser(url = companyInfo.links.website) },
        modifier = Modifier.padding(top = 20.dp),
    ) {
        Text(
            text = stringResource(R.string.see_more),
            style = MaterialTheme.typography.titleMedium,
        )
    }
    Text(
        text = stringResource(R.string.links),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    )
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
    ) {
        SocialImageLink(
            imageUrl = WEBSITE_LOGO_URL,
            url = companyInfo.links.website
        )
        SocialImageLink(
            imageUrl = FLICKR_LOGO_URL,
            url = companyInfo.links.flickr
        )
        SocialImageLink(
            imageUrl = TWITTER_LOGO_URL,
            url = companyInfo.links.twitter
        )
    }
}

@Composable
private fun SocialImageLink(imageUrl: String, url: String) {
    val context = LocalContext.current
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(40.dp)
            .clickable {
                context.openInExternalBrowser(url)
            },
    )
}