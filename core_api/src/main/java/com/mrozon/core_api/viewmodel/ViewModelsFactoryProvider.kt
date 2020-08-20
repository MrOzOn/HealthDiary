package com.mrozon.core_api.viewmodel

import androidx.lifecycle.ViewModelProvider

interface ViewModelsFactoryProvider {

    fun provideViewModel(): ViewModelProvider.Factory
}