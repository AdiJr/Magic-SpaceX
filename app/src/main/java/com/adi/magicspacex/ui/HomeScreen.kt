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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.adi.magicspacex.models.latest_launch.LatestLaunch
import com.adi.magicspacex.models.rockets.Rocket
import com.adi.magicspacex.viewmodels.HomeViewModel

@ExperimentalMaterialApi
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    HomeScreenData(homeViewModel)
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
    homeViewModel.getLatestLaunch()
    val latestLaunch: LatestLaunch? by homeViewModel.latestLaunch.observeAsState()

    Box {
        Image(
            painter = rememberImagePainter(
                data = latestLaunch?.links?.flickr?.original?.first(),
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
            latestLaunch?.let {
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
    homeViewModel.getRockets()
    val rockets: Rocket? by homeViewModel.rockets.observeAsState()

    Column(Modifier.padding(horizontal = 20.dp)) {
        Text(
            "Past missions",
            modifier = Modifier.padding(vertical = 40.dp),
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp)
        )
        rockets?.let { RocketsCarouselSection(list = it) }
        Text(
            "Dragon spaceships",
            modifier = Modifier.padding(vertical = 40.dp),
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp)
        )
        Text(
            "Ships",
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp)
        )
        Text(
            "Landpads",
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp)
        )
        Text(
            "Historical events",
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
private fun RocketsCarouselSection(list: Rocket) {
    Column {
        Text(
            "Rockets",
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        LazyRow {
            items(list.toList()) { rocket ->
                Card(
                    onClick = {},
                    shape = RoundedCornerShape(15.dp),
                    elevation = 15.dp,
                    modifier = Modifier
                        .size(300.dp, 200.dp)
                        .padding(end = 10.dp)
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