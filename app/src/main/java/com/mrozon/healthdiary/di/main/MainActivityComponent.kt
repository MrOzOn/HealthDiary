package com.mrozon.healthdiary.di.main

import com.mrozon.core.CoreProvidersFactory
import com.mrozon.core_api.db.DatabaseProvider
import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.viewmodel.ViewModelsFactoryProvider
import com.mrozon.feature_splash.di.SplashFragmentModule
import com.mrozon.healthdiary.di.FacadeComponent
import com.mrozon.healthdiary.presentation.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [MainActivityModule::class],
    dependencies = [ProvidersFacade::class]
)
interface MainActivityComponent : ViewModelsFactoryProvider {

    companion object {

        fun create(providersFacade: ProvidersFacade): MainActivityComponent {
            return DaggerMainActivityComponent
                .builder()
//                .viewModelsProvider(CoreProvidersFactory.createViewModelBuilder())
                .providersFacade(providersFacade)
                .build()
        }
    }

    fun inject(activity: MainActivity)
}