package com.mrozon.feature_auth.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.network.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import com.mrozon.utils.network.Result
import com.mrozon.utils.network.Result.Companion.error
import com.mrozon.utils.network.Result.Companion.loading
import com.mrozon.utils.network.Result.Companion.success
import timber.log.Timber

@Singleton
class UserAuthRepositoryImpl @Inject constructor(private val userAuthRemoteDataSource: UserAuthRemoteDataSource,
                                                 private val dao: HealthDiaryDao

): UserAuthRepository {

    override fun registerUser(user: User, password: String): Flow<Result<User>> {
        return flow {
            emit(loading())
            val request = RegisterRequest(email = user.email,
                first_name = user.firstname, last_name = user.lastname, password = password, username = user.email)
            val response = userAuthRemoteDataSource.registerUser(request)
            if (response.status == Result.Status.SUCCESS) {
                emit(success(response.data!!.toUser()))
            } else if (response.status == Result.Status.ERROR) {
                emit(error(response.message!!))
            }
        }
    }

    override fun loginUser(userName: String, password: String): Flow<Result<User>> {
        return flow {
            emit(loading())
            val request = LoginRequest(username = userName, password = password)
            val response = userAuthRemoteDataSource.loginUser(request)
            if (response.status == Result.Status.SUCCESS) {
                Timber.d("sahdksdh")
                dao.insertUser(response.data!!.toUserDb())
                emit(success(response.data!!.toUser()))
            } else if (response.status == Result.Status.ERROR) {
                emit(error(response.message!!))
            }
        }
    }
}