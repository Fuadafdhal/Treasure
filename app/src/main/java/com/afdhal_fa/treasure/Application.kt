package com.afdhal_fa.treasure

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}