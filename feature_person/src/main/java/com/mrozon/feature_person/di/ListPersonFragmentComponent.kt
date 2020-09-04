package com.mrozon.feature_person.di

import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.viewmodel.ViewModelsFactoryProvider
import com.mrozon.feature_person.presentation.ListPersonFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ListPersonFragmentModule::class],
    dependencies = [ProvidersFacade::class]
)
interface ListPersonFragmentComponent: ViewModelsFactoryProvider {

    companion object {
        fun create(providersFacade: ProvidersFacade): ListPersonFragmentComponent {
            return DaggerListPersonFragmentComponent.builder()
                .providersFacade(providersFacade)
                .build()
        }
        fun injectFragment(fragment: ListPersonFragment): ListPersonFragmentComponent  {
            val component = create((fragment.activity?.application
                    as AppWithFacade).getFacade())
            component.inject(fragment)
            return component
        }
    }

    fun inject(fragment: ListPersonFragment)
}