package com.adi.magicspacex.ui.screens.home_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.ui.screens.home_screen.composables.*
import com.adi.magicspacex.ui.viewModels.HomeUiState
import com.adi.magicspacex.utils.ui.LoadingSection
import java.util.*

//TODO: Add center top bar, add support for material 3, add theming, add dynamic theming, dark mode support

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    navigateToLaunchDetails: (String) -> Unit,
) {

    val latestLaunch = homeUiState.latestLaunch
    val nextLaunch = homeUiState.nextLaunch

    LoadingSection(isLoading = homeUiState.isLoading) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 25.dp)
        ) {
            if (nextLaunch != null) NextLaunchBanner(nextLaunch, navigateToLaunchDetails)
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

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun LatestLaunchSection(
    launch: Launch,
    navigateToLaunchDetails: (String) -> Unit,
) {
    Box(modifier = Modifier.clickable(onClick = {
        if (launch.id != null) navigateToLaunchDetails(
            launch.id
        )
    })) {
        if (launch.links != null && launch.links.flickr.original.isNotEmpty())
            Image(
                painter = rememberImagePainter(data = launch.links.flickr.original.first(),
                    builder = {
                        crossfade(true)
                    }),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(400.dp)
            )
        if (launch.links != null && launch.links.flickr.original.isNotEmpty())
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent, Color.Black.copy(alpha = 0.7f)
                            ), 0.0f, Float.POSITIVE_INFINITY
                        )
                    )
            )
        if (launch.name != null)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomStart)
                    .padding(start = 30.dp, bottom = 30.dp)
            ) {
                Text(
                    "Latest launch",
                    style = MaterialTheme.typography.h1.copy(
                        color = if (launch.links != null && launch.links.flickr.original.isNotEmpty()) Color.White else Color.Black,
                    ),
                )
                Text(
                    launch.name,
                    style = MaterialTheme.typography.body1.copy(
                        color = if (launch.links != null && launch.links.flickr.original.isNotEmpty()) Color.White else Color.Black,
                        fontSize = 22.sp
                    ),
                )

            }
    }
}

@ExperimentalMaterialApi
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
