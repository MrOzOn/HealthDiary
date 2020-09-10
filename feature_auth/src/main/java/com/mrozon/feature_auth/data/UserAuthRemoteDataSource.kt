package com.mrozon.feature_auth.data

import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.core_api.network.model.LoginRequest
import com.mrozon.core_api.network.model.RegisterRequest
import com.mrozon.utils.base.BaseDataSource
import javax.inject.Inject

class UserAuthRemoteDataSource @Inject constructor(private val service: HealthDiaryService): BaseDataSource() {

    suspend fun loginUser(userName: String, userPsw: String)
            = getResult { service.loginUser(LoginRequest(username = userName, password = userPsw)) }

    suspend fun loginUser(loginRequest: LoginRequest)
            = getResult { service.loginUser(loginRequest) }

    suspend fun registerUser(email: String, password: String, firstName: String, lastName: String)
            = getResult { service.registerUser(RegisterRequest(email = email, username = email,
                password = password, first_name = firstName, last_name = lastName )) }

    suspend fun registerUser(registerRequest: RegisterRequest)
            = getResult { service.registerUser(registerRequest) }

}