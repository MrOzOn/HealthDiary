package com.mrozon.healthdiary.presentation.main

import com.mrozon.healthdiary.di.FragmentScope
import com.mrozon.healthdiary.presentation.showperson.ShowPersonFragment
import com.mrozon.healthdiary.presentation.showperson.ShowPersonFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [ ShowPersonFragmentModule::class])
    fun provideShowPersonFragment(): ShowPersonFragment
}
