package com.mrozon.core_api.security

interface SecurityTokenProvider {
    fun provideSecurityTokenService(): SecurityTokenService
}