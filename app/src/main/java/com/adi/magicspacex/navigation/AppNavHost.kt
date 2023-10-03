package com.adi.magicspacex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.adi.magicspacex.ui.screens.home.navigateToHomeScreen
import com.adi.magicspacex.ui.screens.loader.ROUTE_LOADER
import com.adi.magicspacex.ui.screens.loader.loaderScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_LOADER,
    ) {

        loaderScreen(
            navigateToHomeScreen = navController::navigateToHomeScreen,
        )

        featuresNavGraph(
            navController = navController,
        )
    }
}