package com.mrozon.core_impl.network

import com.mrozon.core_api.network.NetworkProvider
import com.mrozon.core_api.providers.AppProvider
import com.mrozon.core_api.security.SecurityTokenProvider
import com.mrozon.core_api.security.SecurityTokenService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [SecurityTokenProvider::class],
    modules = [NetworkModule::class]
)
interface NetworkComponent : NetworkProvider