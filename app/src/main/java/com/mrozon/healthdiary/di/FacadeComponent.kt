package com.mrozon.healthdiary.di

import android.app.Application
import com.mrozon.core.CoreProvidersFactory
import com.mrozon.core_api.db.DatabaseProvider
import com.mrozon.core_api.network.NetworkProvider
import com.mrozon.core_api.providers.AppProvider
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.viewmodel.ViewModelsFactoryProvider
import com.mrozon.healthdiary.App
import dagger.Component

@Component(
    dependencies = [AppProvider::class, DatabaseProvider::class, NetworkProvider::class]
)
interface FacadeComponent : ProvidersFacade {

    companion object {

        fun init(application: Application): FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .databaseProvider(CoreProvidersFactory.createDatabaseBuilder(AppComponent.create(application)))
                .networkProvider(CoreProvidersFactory.createNetworkBuilder())
                .build()
    }

    fun inject(app: App)
}