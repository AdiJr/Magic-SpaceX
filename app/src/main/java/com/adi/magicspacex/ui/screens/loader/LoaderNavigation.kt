package com.adi.magicspacex.ui.screens.loader

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.adi.magicspacex.utils.model.helpers.DataState
import com.adi.magicspacex.utils.routing.NavRoute

const val ROUTE_LOADER: String = "route_router"
internal val routeLoader = NavRoute(ROUTE_LOADER)

/**
 * Navigation graph description for the app router.
 */
fun NavGraphBuilder.loaderScreen(
    navigateToHomeScreen: () -> Unit,
) {
    composable(route = routeLoader.getRouteWithPlaceholders()) {
        val viewModel: LoaderViewModel = hiltViewModel()
        val viewState by viewModel.viewState.collectAsStateWithLifecycle()

        LaunchedEffect(viewState) {
            if (viewState is DataState.Loaded) {
                navigateToHomeScreen()
            }
        }

        LoaderScreen()
    }
}
