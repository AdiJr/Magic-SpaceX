package com.adi.magicspacex.ui.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import com.adi.magicspacex.ui.screens.home_screen.composables.*
import com.adi.magicspacex.ui.viewModels.HomeUiState
import com.adi.magicspacex.utils.constants.Strings
import com.adi.magicspacex.utils.launchUrl
import com.adi.magicspacex.utils.ui.LoadingSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    navigateToLaunchDetails: (String) -> Unit,
) {
    val latestLaunch = homeUiState.latestLaunch
    val nextLaunch = homeUiState.nextLaunch

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                modifier = Modifier.height(45.dp)
            )
        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            HomeScreenBody(
                homeUiState = homeUiState,
                navigateToLaunchDetails = navigateToLaunchDetails,
                nextLaunch = nextLaunch,
                latestLaunch = latestLaunch,
            )
        }
    }
}

@Composable
private fun HomeScreenBody(
    homeUiState: HomeUiState,
    navigateToLaunchDetails: (String) -> Unit,
    nextLaunch: Launch?,
    latestLaunch: Launch?,
) {
    LoadingSection(isLoading = homeUiState.isLoading) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
        ) {
            if (nextLaunch != null) {
                NextLaunchBanner(nextLaunch, navigateToLaunchDetails)
            }
            if (latestLaunch != null) {
                LatestLaunchSection(latestLaunch, navigateToLaunchDetails)
            }
            homeUiState.companyInfo?.let {
                ContentSection(
                    homeUiState.rockets,
                    homeUiState.pastLaunches,
                    homeUiState.dragons,
                    homeUiState.launchpads,
                    homeUiState.ships,
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
            navigateToLaunchDetails(
                launch.id
            )
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
                    stringResource(R.string.latest_launch),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    launch.name,
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
        stringResource(R.string.about),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 20.dp)
    )
    Text(
        companyInfo.summary,
        style = MaterialTheme.typography.titleMedium.copy(
            textAlign = TextAlign.Justify
        ),
    )
    OutlinedButton(
        { launchUrl(context, companyInfo.links.website) },
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Text(
            stringResource(R.string.see_more),
            style = MaterialTheme.typography.titleMedium,
        )
    }
    Text(
        stringResource(R.string.links),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    )
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(bottom = 20.dp)),
    ) {
        SocialImageLink(
            imageUrl = Strings().websiteLogoUrl,
            url = companyInfo.links.website
        )
        SocialImageLink(
            imageUrl = Strings().flickrLogoUrl,
            url = companyInfo.links.flickr
        )
        SocialImageLink(
            imageUrl = Strings().twitterLogoUrl,
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
                launchUrl(context, url)
            },
    )
}