package com.adi.magicspacex.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val ROUTE_HOME: String = "route_home"

/**
 * Navigation graph description for the app router.
 */
fun NavGraphBuilder.homeScreen(
    navigateToLaunchDetails: (String) -> Unit,
) {
    composable(route = ROUTE_HOME) {
        val viewModel: HomeViewModel = hiltViewModel()
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()

        HomeScreen(
            homeViewState = viewState,
            navigateToLaunchDetails = navigateToLaunchDetails,
        )
    }
}

/**
 * Navigate to [HomeScreen] screen.
 */
internal fun NavController.navigateToHomeScreen() {
    navigate(route = ROUTE_HOME) {
        popUpTo(0)
    }
}
