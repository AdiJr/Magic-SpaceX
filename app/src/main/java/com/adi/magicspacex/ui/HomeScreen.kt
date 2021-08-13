package com.adi.magicspacex.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(text: String) {
    Text(
        text = text,
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentSize(align = Alignment.Center),
        style = TextStyle(color = Color.White, fontSize = 25.sp)
    )
}