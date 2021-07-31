package com.adi.magicspacex.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.adi.magicspacex.utils.theme.MainAppTheme
import java.util.*

@ExperimentalCoilApi
@Composable
fun MyApp() {
    MainAppTheme {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.White
        ) {
            val painter = rememberImagePainter(
                "https://images.unsplash.com/photo-1627680342808-eabaa8cbad88?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",
                builder = {
                    crossfade(true)
                })

            when (painter.state) {
                is ImagePainter.State.Loading -> CircularProgressIndicator()
                is ImagePainter.State.Error -> Text(text = "Error")
                else -> Box {
                    Image(
                        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillHeight,
                        painter = painter,
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 100.dp),
                        text = "Magic Spacex".uppercase(Locale.getDefault()),
                        style = TextStyle(
                            color = Color.White, fontSize = 35.sp
                        )
                    )
                }
            }
        }
    }
}
