package com.adi.magicspacex.utils.routing

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Home : Routes("home")
}