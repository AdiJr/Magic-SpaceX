package com.adi.magicspacex.ui

enum class Screens {
    Splash,
    Home;

    companion object {
        fun fromRoute(route: String?): Screens =
            when (route?.substringBefore("/")) {
                Splash.name -> Splash
                Home.name -> Home
                null -> Home
                else -> throw IllegalArgumentException("Route: $route not recognized")
            }
    }
}