package com.mrozon.feature_person.di

import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.viewmodel.ViewModelsFactoryProvider
import com.mrozon.feature_person.presentation.EditPersonFragment
import com.mrozon.feature_person.presentation.ListPersonFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [EditPersonFragmentModule::class],
    dependencies = [ProvidersFacade::class]
)
interface EditPersonFragmentComponent: ViewModelsFactoryProvider {

    companion object {
        fun create(providersFacade: ProvidersFacade): EditPersonFragmentComponent {
            return DaggerEditPersonFragmentComponent.builder()
                .providersFacade(providersFacade)
                .build()
        }
        fun injectFragment(fragment: EditPersonFragment): EditPersonFragmentComponent  {
            val component = create((fragment.activity?.application
                    as AppWithFacade).getFacade())
            component.inject(fragment)
            return component
        }
    }

    fun inject(fragment: EditPersonFragment)
}