package com.mrozon.healthdiary

import android.app.Application
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initDagger()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun initDagger() {
//        DaggerAppComponent.builder()
//            .application(this)
//            .build()
//            .inject(this)
    }
}

