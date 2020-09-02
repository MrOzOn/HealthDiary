package com.mrozon.feature_auth.di

import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.viewmodel.ViewModelsFactoryProvider
import com.mrozon.feature_auth.presentation.LoginFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [LoginFragmentModule::class],
    dependencies = [ProvidersFacade::class]
)
interface LoginFragmentComponent: ViewModelsFactoryProvider {

    companion object {

        fun create(providersFacade: ProvidersFacade): LoginFragmentComponent {
            return DaggerLoginFragmentComponent.builder()
                .providersFacade(providersFacade)
                .build()
        }

        fun injectFragment(fragment: LoginFragment): LoginFragmentComponent  {
            val component = create((fragment.activity?.application
                    as AppWithFacade).getFacade())
            component.inject(fragment)
            return component
        }
    }

    fun inject(fragment: LoginFragment)
}