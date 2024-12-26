package com.adi.magicspacex.ui.screens.launch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.utils.composables.PagerDotsIndicator
import com.adi.magicspacex.utils.extensions.openInExternalBrowser
import com.adi.magicspacex.utils.formatStringToLocalDateString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchScreen(
    launchDetailsUiState: LaunchDetailsUiState,
    onBackNavigation: () -> Unit,
) {
    val launch = launchDetailsUiState.launch

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (launch?.name != null) {
                            launch.name
                        } else {
                            stringResource(R.string.launch)
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                modifier = Modifier.height(45.dp),
                navigationIcon = {
                    IconButton(
                        onClick = onBackNavigation
                    ) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                }
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

    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(Modifier.padding(horizontal = 20.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (launch?.links != null) {
                    AsyncImage(
                        model = launch.links.patch.large,
                        contentDescription = null,
                        modifier = Modifier
                            .size(350.dp)
                    )
                }

                if (launch?.launchDate != null) {
                    Text(
                        formatStringToLocalDateString(launch.launchDate),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            }

            if (launch?.links != null && launch.links.flickr.original.isNotEmpty()) {
                PagerSection(
                    launch,
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 20.dp),
                color = MaterialTheme.colorScheme.tertiary
            )

            if (launch?.details != null) {
                Text(
                    launch.details,
                    style = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.Justify
                    )
                )
            }

            if (rocket != null && rocket.images.isNotEmpty()) {
                CardSection(
                    stringResource(R.string.rocket),
                    rocket.name,
                    rocket.images.first(),
                )
            }

            if (launchpad != null && launchpad.images.large.isNotEmpty()) {
                CardSection(
                    stringResource(R.string.launchpad),
                    launchpad.fullName,
                    launchpad.images.large.first(),
                )
            }

            if (ship != null) {
                CardSection(stringResource(R.string.ship), ship.name, ship.image)
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 20.dp),
                color = MaterialTheme.colorScheme.tertiary
            )

            if (launch?.links != null) {
                WebcastButton(onWebcastClick = {
                    launch.links.webcast?.let {
                        context.openInExternalBrowser(
                            it
                        )
                    }
                })
            }
        }
    }
}

@Composable
private fun PagerSection(launch: Launch, modifier: Modifier) {
    val imageUrls: List<String> = launch.links.flickr.original
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })

    if (imageUrls.isNotEmpty()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(500.dp)
        ) { index ->
            AsyncImage(
                model = imageUrls[index],
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(500.dp)
            )
        }

        if (imageUrls.size > 1) {
            PagerDotsIndicator(
                totalNumberOfItems = imageUrls.size,
                selectedIndex = pagerState.currentPage,
            )
        }
    }
}

@Composable
private fun CardSection(sectionName: String, name: String, imageUrl: String) {
    Text(
        text = sectionName,
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
                text = name,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
            )
        }
    }
}

@Composable
private fun WebcastButton(onWebcastClick: () -> Unit) {
    Button(
        onClick = onWebcastClick,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Outlined.PlayArrow,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(R.string.watch_webcast),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary,
            )
        )
    }
}
