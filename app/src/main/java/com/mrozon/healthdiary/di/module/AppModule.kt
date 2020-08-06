package com.mrozon.healthdiary.di.module

import com.mrozon.healthdiary.di.ActivityScope
import com.mrozon.healthdiary.presentation.main.MainActivity
import com.mrozon.healthdiary.presentation.main.MainActivityModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Module(includes = [AndroidSupportInjectionModule::class])
interface AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun provideMainActivity(): MainActivity
}
