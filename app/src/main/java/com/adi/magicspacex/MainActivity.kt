package com.adi.magicspacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.adi.magicspacex.ui.SplashScreen
import com.adi.magicspacex.utils.theme.MainAppTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            ProvideWindowInsets {
                MyApp {
                    SplashScreen()
                }
            }
        }
    }
}


@Composable
fun MyApp(content: @Composable () -> Unit) {
    MainAppTheme {
        Surface(color = Color.White) {
            content()
        }
    }
}
