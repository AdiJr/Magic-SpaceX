package com.adi.magicspacex.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.utils.ui.LoadingSection
import com.adi.magicspacex.utils.ui.formatStringToLocalDate
import com.adi.magicspacex.utils.ui.launchUrl
import com.adi.magicspacex.viewmodels.LaunchDetailsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun LaunchScreen(detailsViewModel: LaunchDetailsViewModel = viewModel(), launchId: String) {
    detailsViewModel.fetchLaunchById(launchId)
    val launch: Launch? by detailsViewModel.launch.observeAsState()
    val context = LocalContext.current

    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        LoadingSection(launch) {
            detailsViewModel.fetchRocketById(launch!!.rocket)
            detailsViewModel.fetchLaunchpadById(launch!!.launchpad)
            detailsViewModel.fetchShipById(launch!!.ships.first())

            val rocket: Rocket? by detailsViewModel.rocket.observeAsState()
            val launchpad: Launchpad? by detailsViewModel.launchpad.observeAsState()
            val ship: Ship? by detailsViewModel.ship.observeAsState()

            PagerSection(
                launch!!,
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
            )
            Spacer(Modifier.height(20.dp))
            Column(Modifier.padding(horizontal = 20.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(launch!!.name, style = MaterialTheme.typography.h1.copy(fontSize = 20.sp))
                    Text(
                        formatStringToLocalDate(launch!!.date_utc),
                        style = MaterialTheme.typography.body1.copy(fontSize = 16.sp)
                    )
                    Image(
                        painter = rememberImagePainter(
                            data = launch!!.links.patch.small,
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(250.dp)
                    )
                }
                Divider(color = Color.LightGray, modifier = Modifier.padding(vertical = 20.dp))
                Text(
                    launch!!.details,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify
                    )
                )
                LoadingSection(rocket) {
                    CardSection("Rocket", rocket!!.name, rocket!!.flickr_images.first())
                }
                LoadingSection(launchpad) {
                    CardSection("Launchpad", launchpad!!.name, launchpad!!.images.large.first())
                }
                LoadingSection(ship) {
                    CardSection("Ship", ship!!.name, ship!!.image)
                }
                Divider(color = Color.LightGray, modifier = Modifier.padding(vertical = 20.dp))
                WebcastButton(context, launch!!.links.webcast)
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun PagerSection(launch: Launch, modifier: Modifier) {
    val imageUrls = launch.links.flickr.original
    val pagerState = rememberPagerState(
        pageCount = imageUrls.size,
    )
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.height(400.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrls[it],
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
        )
    }
    HorizontalPagerIndicator(
        pagerState = pagerState,
        activeColor = Color.Blue,
        inactiveColor = Color.Black,
        modifier = modifier,
    )
}

@ExperimentalMaterialApi
@Composable
private fun CardSection(sectionName: String, name: String, imageUrl: String) {
    Text(
        sectionName,
        style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 10.dp)
    )
    Card(
        onClick = {},
        shape = RoundedCornerShape(15.dp),
        elevation = 15.dp,
        modifier = Modifier
            .fillMaxWidth()
            .size(300.dp, 200.dp)
    ) {
        Box {
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
            Box(
                modifier = Modifier
                    .size(350.dp, 200.dp)
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
                name,
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

@Composable
private fun WebcastButton(context: Context, url: String) {
    Button(
        onClick = { launchUrl(context, url) },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.Red,
        ),
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .size(300.dp, 50.dp)
    ) {
        Icon(
            Icons.Outlined.PlayArrow,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            "Watch webcast", style = MaterialTheme.typography.body1.copy(
                fontSize = 16.sp, color = Color.White
            )
        )
    }
}