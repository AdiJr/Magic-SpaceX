package com.adi.magicspacex.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.adi.magicspacex.ui.screens.home.ROUTE_HOME
import com.adi.magicspacex.ui.screens.home.homeScreen

const val ROUTE_GRAPH_FEATURES: String = "route_graph_features"

/**
 * Navigation graph for all the features.
 */
fun NavGraphBuilder.featuresNavGraph(
    navController: NavController,
) {
    navigation(
        startDestination = ROUTE_HOME,
        route = ROUTE_GRAPH_FEATURES,
    ) {

        homeScreen(
            navigateToLaunchDetails = {},
        )
    }
}