package com.mrozon.core_impl.db

import com.mrozon.core_api.db.DatabaseProvider
import com.mrozon.core_api.provides.AppProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppProvider::class],
    modules = [DatabaseModule::class]
)
interface DatabaseComponent : DatabaseProvider