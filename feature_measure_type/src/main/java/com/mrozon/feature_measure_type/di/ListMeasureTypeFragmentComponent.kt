package com.mrozon.feature_measure_type.di

import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.core_api.viewmodel.ViewModelsFactoryProvider
import com.mrozon.feature_measure_type.presentation.ListMeasureTypeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ListMeasureTypeFragmentModule::class],
    dependencies = [ProvidersFacade::class]
)
interface ListMeasureTypeFragmentComponent: ViewModelsFactoryProvider {

    companion object {
        fun create(providersFacade: ProvidersFacade): ListMeasureTypeFragmentComponent {
            return DaggerListMeasureTypeFragmentComponent.builder()
                .providersFacade(providersFacade)
                .build()
        }
        fun injectFragment(fragment: ListMeasureTypeFragment): ListMeasureTypeFragmentComponent  {
            val component = create((fragment.activity?.application
                    as AppWithFacade).getFacade())
            component.inject(fragment)
            return component
        }
    }

    fun inject(fragment: ListMeasureTypeFragment)
}