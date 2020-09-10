package com.mrozon.healthdiary.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.mapper.UserToUserDbMapper
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val healthDiaryDao: HealthDiaryDao,
    private val mapper: UserToUserDbMapper
): UserRepository {

    override fun getLocalUser(): LiveData<User> {
        val userDb = healthDiaryDao.getUser()
        return Transformations.map(userDb) {
            mapper.reverseMap(it)
        }
    }

    override suspend fun clearLocalUser(user: User) {
        val userDb = mapper.map(user)
            userDb?.let {
                healthDiaryDao.deleteUser(userDb)
        }
    }

}
