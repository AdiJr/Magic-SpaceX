package com.adi.magicspacex

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.adi.magicspacex.navigation.AppNavHost
import com.adi.magicspacex.utils.theme.AppTheme
import com.google.accompanist.insets.statusBarsPadding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()

            AppTheme {
                Surface(
                    color = colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                ) {
                    AppNavHost(navController)
                }
            }
        }
    }
}