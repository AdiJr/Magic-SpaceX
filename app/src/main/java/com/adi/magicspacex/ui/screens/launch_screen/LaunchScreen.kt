@file:OptIn(ExperimentalPagerApi::class)

package com.adi.magicspacex.ui.screens.launch_screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
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
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.ui.viewModels.LaunchDetailsUiState
import com.adi.magicspacex.utils.formatStringToLocalDateString
import com.adi.magicspacex.utils.launchUrl
import com.adi.magicspacex.utils.ui.LoadingSection
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchScreen(launchDetailsUiState: LaunchDetailsUiState) {
    val launch = launchDetailsUiState.launch

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        if (launch?.name != null) launch.name else stringResource(R.string.launch),
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
            LaunchScreenBody(launchDetailsUiState = launchDetailsUiState)
        }
    }
}

@Composable
private fun LaunchScreenBody(launchDetailsUiState: LaunchDetailsUiState) {
    val launch = launchDetailsUiState.launch
    val rocket = launchDetailsUiState.rocket
    val launchpad = launchDetailsUiState.launchpad
    val ship = launchDetailsUiState.ship
    val context = LocalContext.current

    LoadingSection(launchDetailsUiState.isLoading) {
        Column(
            Modifier.verticalScroll(rememberScrollState())
        ) {
            Column(Modifier.padding(horizontal = 20.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (launch?.links != null)
                        AsyncImage(
                            model = launch.links.patch.large,
                            contentDescription = null,
                            modifier = Modifier
                                .size(350.dp)
                        )
                    if (launch?.date_utc != null)
                        Text(
                            formatStringToLocalDateString(launch.date_utc),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                }
                if (launch?.links != null && launch.links.flickr.original.isNotEmpty())
                    PagerSection(
                        launch,
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                    )
                Divider(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                if (launch?.details != null)
                    Text(
                        launch.details,
                        style = MaterialTheme.typography.titleMedium.copy(
                            textAlign = TextAlign.Justify
                        )
                    )
                if (rocket != null && rocket.flickr_images.isNotEmpty()) {
                    CardSection(
                        stringResource(R.string.rocket),
                        rocket.name,
                        rocket.flickr_images.first(),
                    )
                }
                if (launchpad != null && launchpad.images.large.isNotEmpty()) {
                    CardSection(
                        stringResource(R.string.launchpad),
                        launchpad.name,
                        launchpad.images.large.first(),
                    )
                }
                if (ship != null) {
                    CardSection(stringResource(R.string.ship), ship.name, ship.image)
                }

                Divider(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                if (launch?.links != null) {
                    WebcastButton(context, launch.links.webcast)
                }
            }
        }
    }
}

@Composable
private fun PagerSection(launch: Launch, modifier: Modifier) {
    val imageUrls: List<String>? = launch.links?.flickr?.original
    val pagerState = imageUrls?.let {
        rememberPagerState(
            pageCount = it.size,
        )
    }
    if (pagerState != null) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(500.dp)
        ) {
            AsyncImage(
                model = imageUrls[it],
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(500.dp)
            )
        }
    }
    if (pagerState != null) {
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.secondary,
            modifier = modifier,
        )
    }
}

@Composable
private fun CardSection(sectionName: String, name: String, imageUrl: String) {
    Text(
        sectionName,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 10.dp)
    )
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth(),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.9f)
                            ),
                            0.0f, Float.POSITIVE_INFINITY
                        )
                    ),
            )
            Text(
                name,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
            )
        }
    }
}

@Composable
private fun WebcastButton(context: Context, url: String) {
    Button(
        onClick = { launchUrl(context, url) },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
    ) {
        Icon(
            Icons.Outlined.PlayArrow,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            stringResource(R.string.watch_webcast),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary,
            )
        )
    }
}