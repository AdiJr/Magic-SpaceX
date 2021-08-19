package com.adi.magicspacex.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.viewmodels.HomeViewModel

@ExperimentalMaterialApi
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val latestLaunchLoaded by homeViewModel.latestLaunchLoaded.observeAsState(false)
    val pastLaunchesLoaded by homeViewModel.pastLaunchesLoaded.observeAsState(false)
    val rocketsLoaded by homeViewModel.rocketsLoaded.observeAsState(false)
    val dragonsLoaded by homeViewModel.dragonsLoaded.observeAsState(false)
    val launchpadsLoaded by homeViewModel.launchpadsLoaded.observeAsState(false)

    if (latestLaunchLoaded && pastLaunchesLoaded && rocketsLoaded && dragonsLoaded && launchpadsLoaded)
        HomeScreenData(homeViewModel)
    else
        CircularProgressIndicator(
            Modifier
                .fillMaxSize()
                .requiredSize(100.dp)
                .padding(30.dp)
        )
}

@ExperimentalMaterialApi
@Composable
private fun HomeScreenData(homeViewModel: HomeViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        LatestLaunchSection(homeViewModel)
        ContentSection(homeViewModel)
    }
}

@Composable
private fun LatestLaunchSection(homeViewModel: HomeViewModel) {
    val launch: Launch? by homeViewModel.launch.observeAsState()

    Box {
        Image(
            painter = rememberImagePainter(
                data = launch?.links?.flickr?.original?.first(),
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.height(400.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        ),
                        0.0f, Float.POSITIVE_INFINITY
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 30.dp, bottom = 30.dp)
        ) {
            Text(
                "Latest launch",
                style = MaterialTheme.typography.h1.copy(
                    color = Color.White,
                ),
            )
            launch?.let {
                Text(
                    it.name,
                    style = MaterialTheme.typography.body1.copy(
                        color = Color.White, fontSize = 22.sp
                    ),
                )
            }
        }
        Button(
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .size(80.dp, 40.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 10.dp, bottom = 10.dp),
            onClick = {}) {
            Text(
                "Details",
                style = MaterialTheme.typography.body1.copy(fontSize = 12.sp)
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun ContentSection(homeViewModel: HomeViewModel) {
    val rockets: List<Rocket>? by homeViewModel.rockets.observeAsState()
    val pastLaunches: List<Launch>? by homeViewModel.pastLaunches.observeAsState()
    val dragons: List<Dragon>? by homeViewModel.dragons.observeAsState()
    val launchpads: List<Launchpad>? by homeViewModel.launchpads.observeAsState()

    Column(Modifier.padding(horizontal = 20.dp)) {
        pastLaunches?.let { PastLaunchesCarouselSection(it) }
        rockets?.let { RocketsCarouselSection(rockets = it) }
        dragons?.let { DragonColumn(it) }
        launchpads?.let { LaunchpadsCarouselSection(it) }
        Text(
            "Ships",
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp)
        )
        Text(
            "About SpaceX",
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp)
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun RocketsCarouselSection(rockets: List<Rocket>) {
    Column {
        Text(
            "Rockets",
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        LazyRow {
            items(rockets.reversed()) { rocket ->
                Card(
                    onClick = {},
                    shape = RoundedCornerShape(15.dp),
                    elevation = 15.dp,
                    modifier = Modifier
                        .size(300.dp, 200.dp)
                        .padding(end = 20.dp)
                ) {
                    Box {
                        Image(
                            painter = rememberImagePainter(
                                data = rocket.flickr_images.first(),
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                        )
                        Box(
                            modifier = Modifier
                                .size(300.dp, 200.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.6f)
                                        ),
                                        0.0f, Float.POSITIVE_INFINITY
                                    )
                                ),
                        )
                        Text(
                            rocket.name,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp),
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 17.sp,
                                color = Color.White
                            ),
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun PastLaunchesCarouselSection(launches: List<Launch>) {
    Column(Modifier.padding(vertical = 20.dp)) {
        Text(
            "Past missions",
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        LazyRow {
            items(
                launches.reversed().subList(1, launches.size)
                    .filter { it.links.flickr.original.isNotEmpty() }) { launch ->
                Card(
                    onClick = {},
                    shape = RoundedCornerShape(15.dp),
                    elevation = 15.dp,
                    modifier = Modifier
                        .size(300.dp, 200.dp)
                        .padding(end = 20.dp)
                ) {
                    Box {
                        Image(
                            painter = rememberImagePainter(
                                data = launch.links.flickr.original.first(),
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                        )
                        Box(
                            modifier = Modifier
                                .size(300.dp, 200.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.6f)
                                        ),
                                        0.0f, Float.POSITIVE_INFINITY
                                    )
                                ),
                        )
                        Text(
                            launch.name,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp),
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 17.sp,
                                color = Color.White
                            ),
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun DragonColumn(dragons: List<Dragon>) {
    Text(
        "Dragon Spaceships",
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
                elevation = 15.dp,
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 20.dp)
            ) {
                Box {
                    Image(
                        painter = rememberImagePainter(
                            data = dragon.flickr_images.first(),
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.scale(1.5f)
                    )
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.8f)
                                    ),
                                    0.0f, Float.POSITIVE_INFINITY
                                )
                            ),
                    )
                    Text(
                        dragon.name,
                        modifier = Modifier
                            .align(Alignment.Center),
                        style = MaterialTheme.typography.h1.copy(
                            fontSize = 22.sp,
                            color = Color.White
                        ),
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun LaunchpadsCarouselSection(launchpads: List<Launchpad>) {
    Column {
        Text(
            "Launchpads",
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        LazyRow {
            items(launchpads.reversed()) { launchpad ->
                Card(
                    onClick = {},
                    shape = RoundedCornerShape(15.dp),
                    elevation = 15.dp,
                    modifier = Modifier
                        .size(300.dp, 200.dp)
                        .padding(end = 20.dp)
                ) {
                    Box {
                        Image(
                            painter = rememberImagePainter(
                                data = launchpad.images.large.first(),
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                        )
                        Box(
                            modifier = Modifier
                                .size(300.dp, 200.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.6f)
                                        ),
                                        0.0f, Float.POSITIVE_INFINITY
                                    )
                                ),
                        )
                        Text(
                            launchpad.full_name,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp),
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 17.sp,
                                color = Color.White
                            ),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}