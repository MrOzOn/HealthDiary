package com.mrozon.feature_auth.data

import com.mrozon.core_api.entity.User
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {
    fun registerUser(user: User, password: String): Flow<Result<User>>
    fun loginUser(userName: String, password: String): Flow<Result<User>>
}