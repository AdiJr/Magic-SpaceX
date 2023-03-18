package com.adi.magicspacex

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adi.magicspacex.ui.screens.home_screen.HomeScreen
import com.adi.magicspacex.ui.screens.launch_screen.LaunchScreen
import com.adi.magicspacex.ui.screens.splash_screen.SplashScreen
import com.adi.magicspacex.ui.viewModels.HomeViewModel
import com.adi.magicspacex.ui.viewModels.LaunchDetailsViewModel
import com.adi.magicspacex.utils.routing.Routes
import com.adi.magicspacex.utils.theme.AppTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProvideWindowInsets {
                MyApp()
            }
        }
    }
}


@Composable
private fun MyApp() {
    AppTheme {
        Surface(
            color = colorScheme.surface,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            val view = LocalView.current
            val isDarkTheme: Boolean = isSystemInDarkTheme()
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            if (!view.isInEditMode) {
                SideEffect {
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                        !isDarkTheme
                }
            }
            CreateNavHost()
        }
    }
}

@Composable
private fun CreateNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route) {
            SplashScreen(
                onContinue = { navController.navigate(Routes.Home.route) },
                onRegister = {},
            )
        }
        composable(Routes.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
            HomeScreen(homeUiState = homeUiState) { launchId ->
                navController.navigate(
                    Routes.LaunchDetails.createRoute(
                        launchId
                    )
                )
            }
        }
        composable(
            "${Routes.LaunchDetails.route}/{launchId}",
            arguments = listOf(navArgument("launchId") {
                type = NavType.StringType
            })
        ) {
            val launchDetailsViewModel = hiltViewModel<LaunchDetailsViewModel>()
            val launchScreenUiState by launchDetailsViewModel.uiState.collectAsStateWithLifecycle()
            LaunchScreen(launchScreenUiState)
        }
    }
}