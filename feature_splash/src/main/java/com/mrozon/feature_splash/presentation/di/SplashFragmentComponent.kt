package com.mrozon.feature_splash.presentation.di

import com.mrozon.core.CoreProvidersFactory
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.viewmodel.ViewModelsProvider
import com.mrozon.feature_splash.presentation.SplashFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

//@Singleton
@Component(
    modules = [SplashFragmentModule::class],
    dependencies = [ProvidersFacade::class, ViewModelsProvider::class]
)
interface SplashFragmentComponent {

    companion object {

        fun create(providersFacade: ProvidersFacade): SplashFragmentComponent {
            return DaggerSplashFragmentComponent
                .builder()
                .providersFacade(providersFacade)
//                .viewModelsProvider(CoreProvidersFactory.createViewModelBuilder())
                .build()
        }
    }

    fun inject(fragment: SplashFragment)
}

//@Subcomponent(modules = [SplashFragmentModule::class])
//interface SplashFragmentComponent {
//
//    @Subcomponent.Factory
//    interface Factory{
//        fun create(): SplashFragmentComponent
//    }
//
//    fun inject(fragment: SplashFragment)
//}