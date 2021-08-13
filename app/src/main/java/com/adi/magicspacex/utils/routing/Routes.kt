package com.adi.magicspacex.utils.routing

import androidx.annotation.StringRes
import com.adi.magicspacex.R

sealed class Routes(val route: String, @StringRes val resourceId: Int) {
    object Splash : Routes("splash", R.string.route_splash)
    object Home : Routes("home", R.string.route_home)
}