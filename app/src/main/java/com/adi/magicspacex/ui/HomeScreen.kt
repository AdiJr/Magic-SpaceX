package com.adi.magicspacex.ui

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.utils.ui.LoadingSection
import com.adi.magicspacex.utils.ui.launchUrl
import com.adi.magicspacex.viewmodels.HomeViewModel

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    navigateToLaunchDetails: (String) -> Unit
) {
    val nextLaunch: Launch? by homeViewModel.nextLaunch.observeAsState()

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 25.dp)
    ) {
        if (nextLaunch != null)
            NextLaunchBanner(nextLaunch!!)
        LatestLaunchSection(homeViewModel, navigateToLaunchDetails)
        ContentSection(homeViewModel)
    }
}

// TODO: Move those composables to seperate files
@Composable
private fun NextLaunchBanner(nextLaunch: Launch) {
    Surface(
        color = Color.LightGray,
        elevation = 15.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = null,
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Upcoming launch: ",
                    style = MaterialTheme.typography.h1.copy(fontSize = 15.sp)
                )
                Text(nextLaunch.name)
            }
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}


@Composable
private fun LatestLaunchSection(
    homeViewModel: HomeViewModel,
    navigateToLaunchDetails: (String) -> Unit
) {
    val launch: Launch? by homeViewModel.launch.observeAsState()

    LoadingSection(data = launch) {
        Box(modifier = Modifier.clickable(onClick = { navigateToLaunchDetails(launch!!.id) })) {
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
    val ships: List<Ship>? by homeViewModel.ships.observeAsState()
    val companyInfo: CompanyInfo? by homeViewModel.companyInfo.observeAsState()

    Column(Modifier.padding(horizontal = 20.dp)) {
        LoadingSection(data = pastLaunches) {
            PastLaunchesCarouselSection(pastLaunches!!)
        }
        LoadingSection(data = rockets) {
            RocketsCarouselSection(rockets!!)
        }
        LoadingSection(data = dragons) {
            DragonColumn(dragons!!)
        }
        LoadingSection(data = launchpads) {
            LaunchpadsCarouselSection(launchpads!!)
        }
        LoadingSection(data = ships) {
            ShipsCarouselSection(ships!!)
        }
        LoadingSection(data = companyInfo) {
            AboutSection(companyInfo!!)
        }
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

@ExperimentalMaterialApi
@Composable
private fun ShipsCarouselSection(ships: List<Ship>) {
    Column {
        Text(
            "Ships",
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
            modifier = Modifier.padding(vertical = 20.dp),
        )
        LazyRow {
            items(ships.reversed()) { ship ->
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
                                data = ship.image,
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
                            ship.name,
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

@Composable
fun AboutSection(companyInfo: CompanyInfo) {
    val context = LocalContext.current

    Text(
        "About SpaceX",
        style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
        modifier = Modifier.padding(vertical = 20.dp)
    )
    Text(
        companyInfo.summary,
        style = MaterialTheme.typography.body1.copy(
            fontSize = 15.sp,
            textAlign = TextAlign.Justify
        ),
    )
    OutlinedButton(onClick = { }) {
        Text(
            "See more",
            style = MaterialTheme.typography.h1.copy(
                fontSize = 15.sp,
                textDecoration = TextDecoration.Underline
            ),
        )
    }
    Text(
        "Links", style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    )
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize(),
    ) {
        SocialImageLink(
            context = context,
            imageUrl = "https://i.pinimg.com/originals/00/50/71/005071cbf1fdd17673607ecd7b7e88f6.png",
            url = companyInfo.links.website
        )
        SocialImageLink(
            context = context,
            imageUrl = "https://www.cabq.gov/social-media/images/flickr.png/@@images/image.png",
            url = companyInfo.links.flickr
        )
        SocialImageLink(
            context = context,
            imageUrl = "https://image.flaticon.com/icons/png/512/124/124021.png",
            url = companyInfo.links.twitter
        )
    }
    Spacer(Modifier.height(20.dp))
}

@Composable
private fun SocialImageLink(context: Context, imageUrl: String, url: String) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                crossfade(true)
            }
        ),
        contentDescription = null,
        modifier = Modifier
            .size(40.dp)
            .clickable {
                launchUrl(context, url)
            }
    )
}