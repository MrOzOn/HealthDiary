package com.mrozon.feature_auth.di

import android.app.Application
import com.mrozon.core.CoreProvidersFactory
import com.mrozon.core_api.db.DatabaseProvider
import com.mrozon.core_api.network.NetworkProvider
import com.mrozon.core_api.providers.AppProvider
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.security.SecurityTokenProvider
import com.mrozon.core_api.viewmodel.ViewModelsFactoryProvider
import com.mrozon.feature_auth.presentation.TestApp
import dagger.Component

@Component(
    dependencies = [AppProvider::class, DatabaseProvider::class, NetworkProvider::class, SecurityTokenProvider::class],
    modules = [NavigationModule::class]
)
interface FacadeComponent : ProvidersFacade {

    companion object {

        fun init(application: Application): FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .databaseProvider(CoreProvidersFactory.createDatabaseBuilder(AppComponent.create(application)))
                .networkProvider(CoreProvidersFactory.createNetworkBuilder())
                .securityTokenProvider(CoreProvidersFactory.createSecurityTokenBuilder(AppComponent.create(application)))
                .build()
    }

    fun inject(app: TestApp)
}
