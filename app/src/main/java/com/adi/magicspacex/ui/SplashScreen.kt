package com.adi.magicspacex.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.adi.magicspacex.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 5000

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val loadingTextComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_text))

    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(Unit) {
        delay(SplashWaitTime)
        currentOnTimeout()
    }

    Column(Modifier.fillMaxSize()) {
        LottieAnimation(
            loadingTextComposition,
            iterations = LottieConstants.IterateForever,
        )
    }
}
