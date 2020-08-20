package com.mrozon.core_api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface ViewModelsProvider {

    fun provideViewModel(): ViewModelProvider.Factory
}