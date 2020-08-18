package com.mrozon.core_impl.db

import com.mrozon.core_api.db.DatabaseProvider
import com.mrozon.core_api.providers.AppProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppProvider::class],
    modules = [DatabaseModule::class]
)
interface DatabaseComponent : DatabaseProvider