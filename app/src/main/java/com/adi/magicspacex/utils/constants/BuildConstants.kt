package com.adi.magicspacex.utils.constants

import android.os.Build
import com.adi.magicspacex.BuildConfig

/**
 * This file contains extensions depending on the build.
 */

/**
 * Global property indicating if application is debuggable.
 */
val isDebug = BuildConfig.DEBUG

/**
 * Global property indicating if application is a beta build.
 */
val isBeta = BuildConfig.BUILD_TYPE == "beta"

/**
 * Global property indicating if application is a release build.
 */
val isRelease = BuildConfig.BUILD_TYPE == "release"

/**
 * Global property indicating if application is running in emulator device.
 */
val isEmulator = Build.FINGERPRINT?.contains("generic") ?: false
