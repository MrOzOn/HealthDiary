package com.mrozon.core_impl.crypto

import com.mrozon.core_api.providers.AppProvider
import com.mrozon.core_api.security.SecurityTokenProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppProvider::class],
    modules = [SecurityTokenModule::class]
)
interface SecurityTokenComponent: SecurityTokenProvider