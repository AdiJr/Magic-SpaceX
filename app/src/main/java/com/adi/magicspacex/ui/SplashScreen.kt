package com.adi.magicspacex.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adi.magicspacex.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SplashScreen(onClick: () -> Unit) {
    val loadingTextComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_text))

    Column(Modifier.fillMaxSize()) {
        Button(
            onClick = onClick,
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        ) {
            Text("Go to next page")
        }
        LottieAnimation(
            loadingTextComposition,
            iterations = LottieConstants.IterateForever,
        )

    }
}