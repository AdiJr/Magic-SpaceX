package com.adi.magicspacex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adi.magicspacex.R

@Composable
fun SplashScreen(onContinue: () -> Unit, onRegister: () -> Unit) = Box {
    Image(
        painter = painterResource(id = R.drawable.splash_background),
        contentDescription = stringResource(R.string.splash_image_description),
        contentScale = ContentScale.FillHeight,
        modifier = Modifier.fillMaxSize(),
    )
    MainTexts()
    BottomButtons(
        onContinue = onContinue,
        onRegister = onRegister,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(20.dp)
    )
}

@Composable
private fun MainTexts() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 150.dp)
    ) {
        Text(
            stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1.copy(fontSize = 30.sp, color = Color.White),
        )
        Text(
            stringResource(id = R.string.splash_quote),
            style = MaterialTheme.typography.body1.copy(
                color = Color.White,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
            ),
            modifier = Modifier.padding(40.dp)
        )
    }
}

@Composable
private fun BottomButtons(onRegister: () -> Unit, onContinue: () -> Unit, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Button(
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White,
                contentColor = Color.Black
            ),
            onClick = onRegister,
        ) {
            Text(stringResource(R.string.register), style = MaterialTheme.typography.body1)
        }
        Spacer(modifier = Modifier.height(10.dp))
        TextButton(onClick = onContinue) {
            Text(
                stringResource(R.string.guest_continue),
                style = MaterialTheme.typography.body1.copy(
                    color = Color.White,
                ),
            )
        }
    }
}

@Preview("SplashScreen")
@Composable
private fun SplashScreenPreview() {
    SplashScreen(onContinue = {}, onRegister = {})
}