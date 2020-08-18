package com.mrozon.core_impl.network

import com.mrozon.core_api.network.NetworkProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class]
)
interface NetworkComponent : NetworkProvider