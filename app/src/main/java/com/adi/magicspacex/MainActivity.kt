package com.adi.magicspacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.adi.magicspacex.ui.HomeScreen
import com.adi.magicspacex.ui.Screens
import com.adi.magicspacex.ui.SplashScreen
import com.adi.magicspacex.utils.theme.MainAppTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            ProvideWindowInsets {
                MyApp()
            }
        }
    }
}


@Composable
fun MyApp() {
    MainAppTheme {
        CreateNavHost()
    }
}

@Composable
fun CreateNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.name
    ) {
        composable(Screens.Splash.name) {
            SplashScreen(onClick = { navController.navigate("${Screens.Home.name}/Adi") })
        }
        composable("${Screens.Home.name}/{text}", arguments = listOf(navArgument("text") {
            type = NavType.StringType
        })) {
            val text = it.arguments?.getString("text")
            HomeScreen(text)
        }

    }
}