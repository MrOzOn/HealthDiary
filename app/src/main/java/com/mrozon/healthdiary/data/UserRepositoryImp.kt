package com.mrozon.healthdiary.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.security.SecurityTokenService
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val dao: HealthDiaryDao,
    private val mapper: UserToUserDbMapper,
    private val securityTokenService: SecurityTokenService
): UserRepository {

    override fun getLocalUser(): LiveData<User> {
        val userDb = dao.getLiveUser()
        return Transformations.map(userDb) {
            mapper.reverseMap(it)
        }
    }

    override suspend fun clearLocalUser(user: User) {
        securityTokenService.clearAccessToken()
        val userDb = mapper.map(user)
            userDb?.let {
                dao.deleteUser(userDb)
        }
    }

}
