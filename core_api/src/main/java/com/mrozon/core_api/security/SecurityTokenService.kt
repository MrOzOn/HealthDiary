package com.mrozon.core_api.security

interface SecurityTokenService {
    fun loadAccessToken(): String
    fun saveAccessToken(string: String)
}