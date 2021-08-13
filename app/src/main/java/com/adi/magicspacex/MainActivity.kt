package com.adi.magicspacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adi.magicspacex.ui.HomeScreen
import com.adi.magicspacex.ui.SplashScreen
import com.adi.magicspacex.utils.routing.Routes
import com.adi.magicspacex.utils.theme.MainAppTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route) {
            SplashScreen(onClick = { navController.navigate(Routes.Home.route) })
        }
        composable(Routes.Home.route) {
            HomeScreen(stringResource(id = Routes.Home.resourceId))
        }

    }
}