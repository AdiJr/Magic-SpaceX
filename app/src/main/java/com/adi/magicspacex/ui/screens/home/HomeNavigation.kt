package com.adi.magicspacex.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.adi.magicspacex.utils.routing.NavRoute

const val ROUTE_HOME: String = "route_home"
internal val routeHome = NavRoute(ROUTE_HOME)

/**
 * Navigation graph description for the app router.
 */
fun NavGraphBuilder.homeScreen(
    navigateToLaunchDetails: (String) -> Unit,
) {
    composable(route = routeHome.getRouteWithPlaceholders()) {
        val viewModel: HomeViewModel = hiltViewModel()
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()

        HomeScreen(
            homeViewState = viewState,
            navigateToLaunchDetails = navigateToLaunchDetails
        )
    }
}

/**
 * Navigate to [HomeScreen] screen.
 */
internal fun NavController.navigateToHomeScreen() {
    navigate(route = routeHome.getRouteWithArguments()) {
        popUpTo(0)
    }
}
