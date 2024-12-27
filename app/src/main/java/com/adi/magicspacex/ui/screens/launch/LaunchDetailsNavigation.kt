package com.adi.magicspacex.ui.screens.launch

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class LaunchDetails(val id: String)

/**
 * Navigation graph builder for the launch details screen.
 */
fun NavGraphBuilder.launchDetails(onBackNavigation: () -> Unit) {
    composable<LaunchDetails> {
        val viewModel: LaunchDetailsViewModel = hiltViewModel()
        val viewState by viewModel.launchDetailsViewStateFlow.collectAsStateWithLifecycle()

        LaunchDetailsScreen(
            viewState = viewState,
            onRefresh = { viewModel.fetchLaunchDetailsData() },
            onBackNavigation = onBackNavigation,
        )
    }
}

/**
 * Navigate to [LaunchDetailsScreen].
 */
fun NavController.navigateToLaunchDetails(id: String) {
    navigate(route = LaunchDetails(id = id))
}