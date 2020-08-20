package com.mrozon.feature_splash.di

import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.viewmodel.ViewModelsFactoryProvider
import com.mrozon.feature_splash.presentation.SplashFragment
import dagger.Component

//@Singleton
@Component(
    modules = [SplashFragmentModule::class],
    dependencies = [ProvidersFacade::class]
)
interface SplashFragmentComponent: ViewModelsFactoryProvider {

    companion object {

        fun create(providersFacade: ProvidersFacade): SplashFragmentComponent {
            return DaggerSplashFragmentComponent.builder()
                .providersFacade(providersFacade)
                .build()
        }

        fun injectFragment(fragment: SplashFragment): SplashFragmentComponent  {
            val component = create((fragment.activity?.application
                as AppWithFacade).getFacade())
            component.inject(fragment)
            return component
        }
    }

    fun inject(fragment: SplashFragment)
}