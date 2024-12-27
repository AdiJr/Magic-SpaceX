package com.adi.magicspacex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adi.magicspacex.ui.screens.home.featuresNavGraph
import com.adi.magicspacex.ui.screens.home.navigateToHomeScreen
import com.adi.magicspacex.ui.screens.intro.IntroScreen
import kotlinx.serialization.Serializable

@Serializable
private data object RouteIntro

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = RouteIntro,
    ) {
        composable<RouteIntro> {
            IntroScreen(onButtonClick = navController::navigateToHomeScreen)
        }

        featuresNavGraph(navController = navController)
    }
}
