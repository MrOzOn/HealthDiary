package com.mrozon.healthdiary

import android.app.Activity
import android.app.Application
import com.mrozon.healthdiary.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}

