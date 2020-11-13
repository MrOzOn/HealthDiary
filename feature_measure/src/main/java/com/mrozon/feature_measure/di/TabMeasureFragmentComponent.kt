package com.mrozon.feature_measure.di

import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.core_api.providers.ProvidersFacade
import com.mrozon.feature_measure.presentation.TabMeasureFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [TabMeasureFragmentModule::class],
    dependencies = [ProvidersFacade::class]
)
interface TabMeasureFragmentComponent {

    companion object {
        fun create(providersFacade: ProvidersFacade): TabMeasureFragmentComponent {
            return DaggerTabMeasureFragmentComponent.builder()
                .providersFacade(providersFacade)
                .build()
        }
        fun injectFragment(fragment: TabMeasureFragment): TabMeasureFragmentComponent  {
            val component = create((fragment.activity?.application
                    as AppWithFacade).getFacade())
            component.inject(fragment)
            return component
        }
    }

    fun inject(fragment: TabMeasureFragment)

}