package com.mrozon.healthdiary

import android.app.Application
import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.healthdiary.di.FacadeComponent
import timber.log.Timber

class App: Application(), AppWithFacade {

    companion object {
        private var facadeComponent: FacadeComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
        (getFacade() as FacadeComponent).inject(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    override fun getFacade(): ProvidersFacade {
        return facadeComponent ?: FacadeComponent.init(this).also { facadeComponent = it }
    }
}

