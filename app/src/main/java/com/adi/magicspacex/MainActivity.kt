package com.adi.magicspacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adi.magicspacex.ui.HomeScreen
import com.adi.magicspacex.ui.SplashScreen
import com.adi.magicspacex.utils.routing.Routes
import com.adi.magicspacex.utils.theme.MainAppTheme
import com.adi.magicspacex.viewmodels.HomeViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                MyApp()
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun MyApp() {
    MainAppTheme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.statusBarsPadding()) {
            CreateNavHost()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CreateNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route) {
            SplashScreen(
                onContinue = { navController.navigate(Routes.Home.route) },
                onRegister = {})
        }
        composable(Routes.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(homeViewModel)
        }
    }
}