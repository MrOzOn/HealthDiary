package com.mrozon.core_api.db

interface DatabaseProvider {

    fun provideDatabase(): HealthDiaryDatabaseContract
    fun healthDiaryDao(): HealthDiaryDao
}