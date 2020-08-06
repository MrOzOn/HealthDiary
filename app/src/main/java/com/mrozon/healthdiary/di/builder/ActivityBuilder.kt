package com.mrozon.healthdiary.di.builder

import com.mrozon.healthdiary.presentation.main.MainActivity
import com.mrozon.healthdiary.presentation.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity
}
