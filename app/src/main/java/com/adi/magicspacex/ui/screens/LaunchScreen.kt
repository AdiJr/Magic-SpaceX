package com.adi.magicspacex.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.ui.viewmodels.LaunchDetailsUiState
import com.adi.magicspacex.utils.formatStringToLocalDateString
import com.adi.magicspacex.utils.launchUrl
import com.adi.magicspacex.utils.ui.LoadingSection
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalCoilApi::class)
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun LaunchScreen(launchDetailsUiState: LaunchDetailsUiState) {
    val context = LocalContext.current
    val launch = launchDetailsUiState.launch

    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        LoadingSection(launchDetailsUiState.isLoading) {
            val rocket = launchDetailsUiState.rocket
            val launchpad = launchDetailsUiState.launchpad
            val ship = launchDetailsUiState.ship

            if (launch?.links != null && launch.links.flickr.original.isNotEmpty())
                PagerSection(
                    launch,
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                )
            Spacer(Modifier.height(30.dp))
            Column(Modifier.padding(horizontal = 20.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (launch?.name != null)
                        Text(
                            launch.name,
                            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp)
                        )
                    if (launch?.date_utc != null)
                        Text(
                            formatStringToLocalDateString(launch.date_utc),
                            style = MaterialTheme.typography.body1.copy(fontSize = 16.sp)
                        )
                    if (launch?.links != null)
                        Image(
                            painter = rememberImagePainter(
                                data = launch.links.patch.small,
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(250.dp)
                        )
                }
                Divider(color = Color.LightGray, modifier = Modifier.padding(vertical = 20.dp))
                if (launch?.details != null)
                    Text(
                        launch.details,
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Justify
                        )
                    )
                LoadingSection(launchDetailsUiState.isLoading) {
                    if (rocket != null && rocket.flickr_images.isNotEmpty())
                        CardSection("Rocket", rocket.name, rocket.flickr_images.first())
                    if (launchpad != null && launchpad.images.large.isNotEmpty())
                        CardSection("Launchpad", launchpad.name, launchpad.images.large.first())
                    if (ship != null)
                        CardSection("Ship", ship.name, ship.image)

                }

                Divider(color = Color.LightGray, modifier = Modifier.padding(vertical = 20.dp))
                if (launch?.links != null)
                    WebcastButton(context, launch.links.webcast)
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@ExperimentalPagerApi
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
    }
    if (pagerState != null) {
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = Color.Blue,
            inactiveColor = Color.Black,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
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