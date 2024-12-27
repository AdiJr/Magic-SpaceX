package com.adi.magicspacex.ui.screens.intro

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.adi.magicspacex.R
import com.adi.magicspacex.utils.composables.HorizontalSpacer
import com.adi.magicspacex.utils.composables.LogoBanner
import com.adi.magicspacex.utils.composables.PagerDotsIndicator
import com.adi.magicspacex.utils.composables.VerticalSpacer
import kotlin.math.abs

@Composable
fun IntroScreen(onButtonClick: () -> Unit) {
    val context = LocalContext.current
    val view = LocalView.current

    SideEffect {
        val window = (context as Activity).window
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }

    val pagerContentList = listOf(
        PagerContent(
            quote = "The important achievement of Apollo was demonstrating that humanity is not forever chained to the Earth and that our visions go rather further than that and our opportunities are unlimited",
            quoteAuthor = "Neil Armstrong",
            pageTitle = "Welcome to Magic SpaceX",
            pageDescription = "Discover the latest and upcoming SpaceX missions, with detailed information about every launch",
            backgroundImageDrawableResId = R.drawable.splash_background_1,
        ),
        PagerContent(
            quote = "Space exploration is a force of nature unto itself that no other force in society can rival",
            quoteAuthor = "Neil deGrasse Tyson",
            pageTitle = "Track Past Launches",
            pageDescription = "Explore SpaceX’s past launches and missions, with records and highlights of historic moments in space exploration",
            backgroundImageDrawableResId = R.drawable.splash_background_2,
        ),
        PagerContent(
            quote = "To confine our attention to terrestrial matters would be to limit the human spirit",
            quoteAuthor = "Stephen Hawking",
            pageTitle = "Stay Updated",
            pageDescription = "Get real-time updates on upcoming launches, mission status, and much more, keeping you connected to SpaceX’s journey into the stars",
            backgroundImageDrawableResId = R.drawable.splash_background_3,
        )
    )

    val pagerState = rememberPagerState { pagerContentList.size }

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
    ) { currentPage ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = pagerContentList[currentPage].backgroundImageDrawableResId),
            contentDescription = stringResource(R.string.splash_image_description),
            contentScale = ContentScale.FillHeight,
        )
    }

    val textsAnimatedOpacity by animateFloatAsState(
        targetValue = 1f - abs(pagerState.currentPageOffsetFraction),
        animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing),
        label = "text opacity animation"
    )

    val textsAnimatedScale by animateFloatAsState(
        targetValue = (1f - abs(pagerState.currentPageOffsetFraction)).coerceIn(0.9f, 1f),
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        label = "text opacity animation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0f to Color.Black.copy(alpha = 0.8f),
                    0.4f to Color.Black.copy(alpha = 0.4f),
                    0.7f to Color.Black.copy(alpha = 0.8f),
                    1.0f to Color.Transparent,
                )
            )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LogoBanner()

            VerticalSpacer(height = 30.dp)

            Text(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .graphicsLayer {
                        alpha = textsAnimatedOpacity
                        scaleX = textsAnimatedScale
                        scaleY = textsAnimatedScale
                    },
                text = pagerContentList[pagerState.currentPage].quote,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    textMotion = TextMotion.Animated,
                ),
            )

            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 10.dp, end = 20.dp)
                    .graphicsLayer {
                        alpha = textsAnimatedOpacity
                        scaleX = textsAnimatedScale
                        scaleY = textsAnimatedScale
                    },
                text = pagerContentList[pagerState.currentPage].quoteAuthor,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White,
                    textAlign = TextAlign.End,
                    fontStyle = FontStyle.Italic,
                ),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.graphicsLayer {
                    alpha = textsAnimatedOpacity
                    scaleX = textsAnimatedScale
                    scaleY = textsAnimatedScale
                },
                text = pagerContentList[pagerState.currentPage].pageTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                ),
            )

            VerticalSpacer(height = 10.dp)

            Text(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .graphicsLayer {
                        alpha = textsAnimatedOpacity
                        scaleX = textsAnimatedScale
                        scaleY = textsAnimatedScale
                    },
                text = pagerContentList[pagerState.currentPage].pageDescription,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                ),
            )

            VerticalSpacer(height = 30.dp)

            OutlinedButton(
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                onClick = onButtonClick,
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Learn more",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    HorizontalSpacer(width = 5.dp)

                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null,
                    )
                }
            }

            VerticalSpacer(height = 40.dp)

            PagerDotsIndicator(
                totalNumberOfItems = pagerState.pageCount,
                selectedColor = MaterialTheme.colorScheme.onSecondary,
                selectedIndex = pagerState.currentPage,
                unselectedColor = MaterialTheme.colorScheme.secondary,
            )

            VerticalSpacer(height = 20.dp)
        }
    }
}

private data class PagerContent(
    val quote: String,
    val quoteAuthor: String,
    val pageTitle: String,
    val pageDescription: String,
    @DrawableRes val backgroundImageDrawableResId: Int,
)

@Preview
@Composable
private fun LoadScreenPreview() {
    IntroScreen(onButtonClick = {})
}
