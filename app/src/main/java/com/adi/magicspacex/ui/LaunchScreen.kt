package com.adi.magicspacex.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.utils.ui.LoadingSection
import com.adi.magicspacex.utils.ui.formatStringToLocalDate
import com.adi.magicspacex.utils.ui.launchUrl
import com.adi.magicspacex.viewmodels.LaunchDetailsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun LaunchScreen(detailsViewModel: LaunchDetailsViewModel = viewModel()) {
    val launch: Launch? by detailsViewModel.launch.observeAsState()
    val context = LocalContext.current

    Column(
        Modifier.verticalScroll(rememberScrollState())
    ) {
        LoadingSection(launch) {
            val imageUrls = launch!!.links.flickr.original
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
                modifier = Modifier
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
                Divider(color = Color.LightGray, modifier = Modifier.padding(vertical = 20.dp))
                Button(
                    onClick = { launchUrl(context, launch!!.links.webcast) },
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
        }
    }

}