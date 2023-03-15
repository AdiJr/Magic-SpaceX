package com.adi.magicspacex.utils.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingSection(isLoading: Boolean, content: @Composable () -> Unit) {
    if (isLoading) {
        CircularProgressIndicator(
            Modifier
                .fillMaxSize()
                .requiredSize(100.dp)
                .padding(30.dp)
        )
    } else {
        content()
    }
}