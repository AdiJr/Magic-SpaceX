@file:OptIn(ExperimentalMaterial3Api::class)

package com.adi.magicspacex.ui.screens.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adi.magicspacex.R
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.utils.composables.VerticalSpacer

@Composable
fun RocketsCarouselSection(rockets: List<Rocket>) {
    Text(
        text = stringResource(R.string.rockets),
        style = MaterialTheme.typography.titleLarge,
    )

    VerticalSpacer(height = 10.dp)

    LazyRow {
        items(rockets.reversed()) { rocket ->
            CarouselItem(
                imageUrl = rocket.flickr_images.first(),
                cardText = rocket.name,
                onClick = {},
            )
        }
    }
}

@Composable
fun PastLaunchesCarouselSection(
    launches: List<Launch>,
    navigateToLaunchDetails: (String) -> Unit,
) {
    Text(
        text = stringResource(R.string.past_missions),
        style = MaterialTheme.typography.titleLarge,
    )

    VerticalSpacer(height = 10.dp)

    LazyRow {
        items(launches.reversed().subList(1, launches.size)
            .filter { it.links != null && it.links.flickr.original.isNotEmpty() }
        ) { launch ->
            if (launch.links != null && launch.name != null)
                CarouselItem(
                    imageUrl = launch.links.flickr.original.first(),
                    cardText = launch.name,
                    onClick = { if (launch.id != null) navigateToLaunchDetails(launch.id) },
                )
        }
    }
}

@Composable
fun DragonSection(dragons: List<Dragon>) {
    Text(
        text = stringResource(R.string.dragons),
        style = MaterialTheme.typography.titleLarge,
    )

    VerticalSpacer(height = 10.dp)

    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        dragons.map { dragon ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                shape = RoundedCornerShape(15.dp),
                onClick = {},
            ) {
                Box {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .scale(1.7f),
                        model = dragon.flickr_images.first(),
                        contentDescription = null,
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.8f)
                                    ),
                                )
                            ),
                    )

                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = dragon.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun LaunchpadsCarouselSection(launchpads: List<Launchpad>) {
    Text(
        text = stringResource(R.string.launchpads),
        style = MaterialTheme.typography.titleLarge,
    )

    VerticalSpacer(height = 10.dp)

    LazyRow {
        items(launchpads.reversed()) { launchpad ->
            CarouselItem(
                imageUrl = launchpad.images.large.first(),
                cardText = launchpad.full_name,
                onClick = {},
            )
        }
    }
}

@Composable
fun ShipsCarouselSection(ships: List<Ship>) {
    Text(
        text = stringResource(R.string.ships),
        style = MaterialTheme.typography.titleLarge,
    )

    VerticalSpacer(height = 10.dp)

    LazyRow {
        items(ships.reversed()) { ship ->
            CarouselItem(
                imageUrl = ship.image,
                cardText = ship.name,
                onClick = {},
            )
        }
    }
}

@Composable
private fun CarouselItem(
    imageUrl: String,
    cardText: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .size(width = 300.dp, height = 200.dp)
            .padding(end = 20.dp),
        shape = RoundedCornerShape(15.dp),
        onClick = onClick,
    ) {
        Box {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(width = 300.dp, height = 200.dp)
            )

            Box(
                modifier = Modifier
                    .size(width = 300.dp, height = 200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f),
                            ),
                        )
                    ),
            )

            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                text = cardText,
                textAlign = TextAlign.Center,
                softWrap = true,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                ),
            )
        }
    }
}
