package com.mrozon.feature_auth.data

import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.core_api.network.model.LoginRequest
import com.mrozon.core_api.network.model.RegisterRequest
import com.mrozon.utils.base.BaseDataSource
import javax.inject.Inject

class UserAuthRemoteDataSource @Inject constructor(private val service: HealthDiaryService): BaseDataSource() {

    suspend fun loginUser(loginRequest: LoginRequest)
            = getResult { service.loginUser(loginRequest) }

    suspend fun registerUser(registerRequest: RegisterRequest)
            = getResult { service.registerUser(registerRequest) }

}