package com.mrozon.healthdiary

import android.app.Activity
import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
//        DaggerAppComponent.builder()
//            .application(this)
//            .build()
//            .inject(this)
    }
}

