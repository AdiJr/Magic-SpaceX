package com.adi.magicspacex.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.adi.magicspacex.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SplashScreen() {
    val loadingTextComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_text))

    Column(Modifier.fillMaxSize()) {
        LottieAnimation(
            loadingTextComposition,
            iterations = LottieConstants.IterateForever,
        )
    }
}
