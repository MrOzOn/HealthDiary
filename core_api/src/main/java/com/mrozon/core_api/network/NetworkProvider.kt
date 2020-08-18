package com.mrozon.core_api.network

interface NetworkProvider {

    fun provideNetworkService(): HealthDiaryService
}