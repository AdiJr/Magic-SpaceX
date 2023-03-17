package com.adi.magicspacex.ui.screens.home_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.adi.magicspacex.R
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship

@ExperimentalMaterialApi
@Composable
fun RocketsCarouselSection(rockets: List<Rocket>) {
    Column {
        Text(
            stringResource(R.string.rockets),
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 10.dp)
        )
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
}

@ExperimentalMaterialApi
@Composable
fun PastLaunchesCarouselSection(
    launches: List<Launch>, navigateToLaunchDetails: (String) -> Unit
) {
    Column(Modifier.padding(vertical = 20.dp)) {
        Text(
            stringResource(R.string.past_missions),
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        LazyRow {
            items(launches.reversed().subList(1, launches.size)
                .filter { it.links != null && it.links.flickr.original.isNotEmpty() }) { launch ->
                if (launch.links != null && launch.name != null)
                    CarouselItem(
                        imageUrl = launch.links.flickr.original.first(),
                        cardText = launch.name,
                        onClick = { if (launch.id != null) navigateToLaunchDetails(launch.id) },
                    )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DragonColumn(dragons: List<Dragon>) {
    Text(
        stringResource(R.string.dragons),
        modifier = Modifier.padding(vertical = 20.dp),
        style = MaterialTheme.typography.h1.copy(fontSize = 20.sp)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        dragons.map { dragon ->
            Card(
                onClick = {},
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .size(350.dp)
                    .padding(bottom = 20.dp)
            ) {
                Box {
                    AsyncImage(
                        model = dragon.flickr_images.first(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(350.dp)
                            .scale(1.5f),
                    )
                    Box(
                        modifier = Modifier
                            .size(350.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent, Color.Black.copy(alpha = 0.8f)
                                    ), 0.0f, Float.POSITIVE_INFINITY
                                )
                            ),
                    )
                    Text(
                        dragon.name,
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.h1.copy(
                            fontSize = 22.sp, color = Color.White
                        ),
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun LaunchpadsCarouselSection(launchpads: List<Launchpad>) {
    Column {
        Text(
            stringResource(R.string.launchpads),
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 10.dp)
        )
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
}

@ExperimentalMaterialApi
@Composable
fun ShipsCarouselSection(ships: List<Ship>) {
    Column {
        Text(
            stringResource(R.string.ships),
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
            modifier = Modifier.padding(vertical = 20.dp),
        )
        LazyRow {
            items(ships.reversed()) { ship ->
                CarouselItem(imageUrl = ship.image, cardText = ship.name, onClick = {})
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CarouselItem(
    imageUrl: String,
    cardText: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .size(300.dp, 200.dp)
            .padding(end = 20.dp)
    ) {
        Box {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(300.dp, 200.dp)
            )
            Box(
                modifier = Modifier
                    .size(300.dp, 200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent, Color.Black.copy(alpha = 0.6f)
                            ), 0.0f, Float.POSITIVE_INFINITY
                        )
                    ),
            )
            Text(
                cardText,
                softWrap = true,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 17.sp, color = Color.White,
                ),
            )
        }
    }
}
