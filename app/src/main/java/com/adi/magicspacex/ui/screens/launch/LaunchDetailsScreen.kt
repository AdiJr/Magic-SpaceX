package com.adi.magicspacex.ui.screens.launch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.adi.magicspacex.R
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.utils.composables.FullScreenError
import com.adi.magicspacex.utils.composables.FullScreenLoading
import com.adi.magicspacex.utils.composables.PagerDotsIndicator
import com.adi.magicspacex.utils.composables.VerticalSpacer
import com.adi.magicspacex.utils.extensions.openInExternalBrowser
import com.adi.magicspacex.utils.formatStringToLocalDateString
import com.adi.magicspacex.utils.model.helpers.DataState
import com.adi.magicspacex.utils.model.helpers.State

@Composable
fun LaunchDetailsScreen(
    viewState: DataState<LaunchDetailsViewState>,
    onRefresh: () -> Unit,
    onBackNavigation: () -> Unit,
) {
    when (viewState) {
        State.Idle, State.Loading -> {
            FullScreenLoading()
        }

        is DataState.Loaded -> {
            LaunchScreenContent(
                viewStateDataState = viewState,
                viewState = viewState.data,
                onRefresh = onRefresh,
                onBackNavigation = onBackNavigation,
            )
        }

        is State.Error -> {
            FullScreenError()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LaunchScreenContent(
    viewStateDataState: DataState<LaunchDetailsViewState>,
    viewState: LaunchDetailsViewState,
    onRefresh: () -> Unit,
    onBackNavigation: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        PullToRefreshBox(
            isRefreshing = viewStateDataState is State.Loading,
            onRefresh = onRefresh,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            modifier = Modifier.padding(start = 20.dp),
                            onClick = onBackNavigation,
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Back button",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }

                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = viewState.launch.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }

                VerticalSpacer(height = 20.dp)

                LaunchScreenBody(viewState = viewState)
            }
        }
    }
}

@Composable
private fun LaunchScreenBody(
    viewState: LaunchDetailsViewState,
) {
    val launch = viewState.launch
    val rocket = viewState.rocket
    val launchpad = viewState.launchpad
    val context = LocalContext.current

    Column(Modifier.padding(horizontal = 20.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = launch.links.patch.large,
                contentDescription = null,
                modifier = Modifier.size(350.dp)
            )

            Text(
                formatStringToLocalDateString(launch.launchDate),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        if (launch.links.flickr.original.isNotEmpty()) {
            PagerSection(
                launch,
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            color = MaterialTheme.colorScheme.tertiary
        )

        Text(
            launch.details,
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Justify
            )
        )

        if (rocket.images.isNotEmpty()) {
            CardSection(
                stringResource(R.string.rocket),
                rocket.name,
                rocket.images.first(),
            )
        }

        if (launchpad.images.large.isNotEmpty()) {
            CardSection(
                stringResource(R.string.launchpad),
                launchpad.fullName,
                launchpad.images.large.first(),
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            color = MaterialTheme.colorScheme.tertiary
        )

        WebcastButton(onWebcastClick = { context.openInExternalBrowser(launch.links.webcast) })
    }
}

@Composable
private fun PagerSection(launch: Launch) {
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
