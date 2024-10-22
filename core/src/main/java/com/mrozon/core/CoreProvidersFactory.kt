package com.mrozon.core

import com.mrozon.core_api.db.DatabaseProvider
import com.mrozon.core_api.network.NetworkProvider
import com.mrozon.core_api.providers.AppProvider
import com.mrozon.core_api.security.SecurityTokenProvider
import com.mrozon.core_impl.crypto.DaggerSecurityTokenComponent
import com.mrozon.core_impl.db.DaggerDatabaseComponent
import com.mrozon.core_impl.network.DaggerNetworkComponent

object CoreProvidersFactory {
    fun createDatabaseBuilder(appProvider: AppProvider): DatabaseProvider {
        return DaggerDatabaseComponent.builder().appProvider(appProvider).build()
    }

    fun createNetworkBuilder(appProvider: AppProvider): NetworkProvider {
        return DaggerNetworkComponent.builder().securityTokenProvider(createSecurityTokenBuilder(appProvider)).build()
    }

    fun createSecurityTokenBuilder(appProvider: AppProvider): SecurityTokenProvider {
        return DaggerSecurityTokenComponent.builder().appProvider(appProvider).build()
    }
}