package com.adi.magicspacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.adi.magicspacex.ui.HomeScreen
import com.adi.magicspacex.ui.LaunchScreen
import com.adi.magicspacex.ui.SplashScreen
import com.adi.magicspacex.utils.routing.Routes
import com.adi.magicspacex.utils.theme.MainAppTheme
import com.adi.magicspacex.viewmodels.HomeViewModel
import com.adi.magicspacex.viewmodels.LaunchDetailsViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                MyApp()
            }
        }
    }
}


@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
private fun MyApp() {
    MainAppTheme {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            CreateNavHost()
        }
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
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
                onRegister = {})
        }
        composable(Routes.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                homeViewModel,
                navigateToLaunchDetails =
                { launchId -> navController.navigate(Routes.LaunchDetails.createRoute(launchId)) })
        }
        composable(
            "${Routes.LaunchDetails.route}/{launchId}",
            arguments = listOf(navArgument("launchId") {
                type = NavType.StringType
            })
        ) {
            val launchId = it.arguments!!.getString("launchId")!!
            val launchDetailsViewModel = hiltViewModel<LaunchDetailsViewModel>()
            launchDetailsViewModel.fetchLaunchById(launchId)
            LaunchScreen(launchDetailsViewModel)
        }
    }
}