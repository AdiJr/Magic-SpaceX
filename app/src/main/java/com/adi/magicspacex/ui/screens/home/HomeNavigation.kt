package com.adi.magicspacex.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreen

/**
 * Navigation graph builder for the home screen.
 */
fun NavGraphBuilder.homeScreen(
    navigateToLaunchDetails: (launchId: String) -> Unit,
) {
    composable<HomeScreen> {
        val viewModel: HomeViewModel = hiltViewModel()
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()

        HomeScreen(
            homeViewState = viewState,
            onNavigationToLaunchDetails = navigateToLaunchDetails,
        )
    }
}

/**
 * Navigate to [HomeScreen] screen.
 */
internal fun NavController.navigateToHomeScreen() {
    navigate(route = HomeScreen) {
        popUpTo(0)
    }
}
