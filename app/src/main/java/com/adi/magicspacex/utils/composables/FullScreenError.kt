package com.adi.magicspacex.utils.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

/**
 * Full screen error composable.
 */
@Composable
fun FullScreenError() {
    val lottieCompositionError by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_error)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LogoBanner(
            modifier = Modifier.padding(top = 100.dp),
            textColor = MaterialTheme.colorScheme.onSurface,
        )

        LottieAnimation(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.Center),
            composition = lottieCompositionError,
            iterations = LottieConstants.IterateForever,
        )

        Text(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomCenter),
            text = "SOMETHING WENT WRONG",
        )
    }
}

