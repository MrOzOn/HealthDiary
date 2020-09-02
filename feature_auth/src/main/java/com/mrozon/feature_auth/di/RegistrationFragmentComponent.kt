package com.mrozon.feature_auth.di

import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.viewmodel.ViewModelsFactoryProvider
import com.mrozon.feature_auth.presentation.LoginFragment
import com.mrozon.feature_auth.presentation.RegistrationFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RegistrationFragmentModule::class],
    dependencies = [ProvidersFacade::class]
)
interface RegistrationFragmentComponent: ViewModelsFactoryProvider {

    companion object {

        fun create(providersFacade: ProvidersFacade): RegistrationFragmentComponent {
            return DaggerRegistrationFragmentComponent.builder()
                .providersFacade(providersFacade)
                .build()
        }

        fun injectFragment(fragment: RegistrationFragment): RegistrationFragmentComponent  {
            val component = create((fragment.activity?.application
                    as AppWithFacade).getFacade())
            component.inject(fragment)
            return component
        }
    }

    fun inject(fragment: RegistrationFragment)
}