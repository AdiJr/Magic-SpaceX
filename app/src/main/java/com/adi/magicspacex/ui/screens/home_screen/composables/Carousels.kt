package com.adi.magicspacex.ui.screens.home_screen.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.adi.magicspacex.R
import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.utils.launchUrl

//TODO: move same row corousel component to new composable and reuse it in each section

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
                Card(
                    onClick = {},
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .size(300.dp, 200.dp)
                        .padding(end = 20.dp)
                ) {
                    Box {
                        AsyncImage(
                            model = rocket.flickr_images.first(),
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
                            rocket.name,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp),
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 17.sp, color = Color.White
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
                Card(
                    onClick = { if (launch.id != null) navigateToLaunchDetails(launch.id) },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .size(300.dp, 200.dp)
                        .padding(end = 20.dp)
                ) {
                    Box {
                        if (launch.links != null)
                            AsyncImage(
                                model = launch.links.flickr.original.first(),
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
                        if (launch.name != null)
                            Text(
                                launch.name,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(10.dp),
                                style = MaterialTheme.typography.body1.copy(
                                    fontSize = 17.sp, color = Color.White
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
                Card(
                    onClick = {},
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .size(300.dp, 200.dp)
                        .padding(end = 20.dp)
                ) {
                    Box {
                        AsyncImage(
                            model = launchpad.images.large.first(),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(300.dp, 200.dp),
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
                            launchpad.full_name,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp),
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 17.sp, color = Color.White
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
fun ShipsCarouselSection(ships: List<Ship>) {
    Column {
        Text(
            stringResource(R.string.ships),
            style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
            modifier = Modifier.padding(vertical = 20.dp),
        )
        LazyRow {
            items(ships.reversed()) { ship ->
                Card(
                    onClick = {},
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .size(300.dp, 200.dp)
                        .padding(end = 20.dp)
                ) {
                    Box {
                        AsyncImage(
                            model = ship.image,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(300.dp, 200.dp),
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
                            ship.name,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp),
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 17.sp, color = Color.White
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
        stringResource(R.string.about),
        style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
        modifier = Modifier.padding(vertical = 20.dp)
    )
    Text(
        companyInfo.summary,
        style = MaterialTheme.typography.body1.copy(
            fontSize = 15.sp, textAlign = TextAlign.Justify
        ),
    )
    OutlinedButton(onClick = { }) {
        Text(
            stringResource(R.string.see_more),
            style = MaterialTheme.typography.h1.copy(
                fontSize = 15.sp, textDecoration = TextDecoration.Underline
            ),
        )
    }
    Text(
        stringResource(R.string.links),
        style = MaterialTheme.typography.h1.copy(fontSize = 20.sp),
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
fun SocialImageLink(context: Context, imageUrl: String, url: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(40.dp)
            .clickable {
                launchUrl(context, url)
            })
}