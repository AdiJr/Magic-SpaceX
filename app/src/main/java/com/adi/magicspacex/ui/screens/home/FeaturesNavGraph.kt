package com.adi.magicspacex.ui.screens.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.adi.magicspacex.ui.screens.launch.launchDetails
import com.adi.magicspacex.ui.screens.launch.navigateToLaunchDetails
import kotlinx.serialization.Serializable

@Serializable
private data object RouteNestedHome

/**
 * Nested navigation graph for the screens launched from the main app flow, after introduction.
 */
fun NavGraphBuilder.featuresNavGraph(
    navController: NavController,
) {
    navigation<RouteNestedHome>(startDestination = HomeScreen) {
        homeScreen(
            navigateToLaunchDetails = { launchId ->
                navController.navigateToLaunchDetails(id = launchId)
            },
        )

        launchDetails(
            onBackNavigation = {
                navController.popBackStack()
            },
        )
    }
}

