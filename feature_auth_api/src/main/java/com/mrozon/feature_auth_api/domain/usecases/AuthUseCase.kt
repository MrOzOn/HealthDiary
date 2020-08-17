package com.mrozon.feature_auth_api.domain.usecases

import com.mrozon.feature_auth_api.domain.entities.User

interface AuthUseCase {
    fun isLoggedIn(user: User): Boolean
    fun registerUser(user: User): Boolean
    fun loginUser(user: User): Boolean
    fun logoutUser(user: User): Boolean
}