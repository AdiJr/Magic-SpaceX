package com.adi.magicspacex.utils

import android.app.Application
import com.adi.magicspacex.utils.constants.isDebug
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupLogging()
    }

    private fun setupLogging() {
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO: Send logs to external crash reporting system
        }
    }
}
