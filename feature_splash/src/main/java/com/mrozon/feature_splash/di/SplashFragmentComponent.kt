package com.mrozon.feature_splash.di

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mrozon.core.CoreProvidersFactory
import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.viewmodel.ViewModelsFactoryProvider
import com.mrozon.feature_splash.presentation.SplashFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
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