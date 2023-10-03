package com.adi.magicspacex.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.WindowManager
import com.adi.magicspacex.utils.constants.isDebug
import com.adi.magicspacex.utils.constants.isRelease
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupLogging()
        setupScreenshotProtection()
    }

    private fun setupLogging() {
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO: Send logs to external crash reporting system
        }
    }

    private fun setupScreenshotProtection() {
        if (isRelease) {
            registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                    activity.window.setFlags(
                        WindowManager.LayoutParams.FLAG_SECURE,
                        WindowManager.LayoutParams.FLAG_SECURE,
                    )
                }

                override fun onActivityStarted(p0: Activity) {
                    // nothing to do
                }

                override fun onActivityResumed(p0: Activity) {
                    // nothing to do
                }

                override fun onActivityPaused(p0: Activity) {
                    // nothing to do
                }

                override fun onActivityStopped(p0: Activity) {
                    // nothing to do
                }

                override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                    // nothing to do
                }

                override fun onActivityDestroyed(p0: Activity) {
                    // nothing to do
                }
            })
        }
    }
}