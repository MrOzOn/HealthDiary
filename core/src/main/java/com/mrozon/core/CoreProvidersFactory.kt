package com.mrozon.core

import com.mrozon.core_api.db.DatabaseProvider
import com.mrozon.core_api.provides.AppProvider
import com.mrozon.core_impl.db.DaggerDatabaseComponent

object CoreProvidersFactory {
    fun createDatabaseBuilder(appProvider: AppProvider): DatabaseProvider {
        return DaggerDatabaseComponent.builder().appProvider(appProvider).build()
    }
}