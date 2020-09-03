package com.mrozon.healthdiary.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.network.model.toUserDb
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class LocalUserDataSourceImp @Inject constructor(
    private val healthDiaryDao: HealthDiaryDao,
    private val mapper: UserToUserDbMapper
): LocalUserDataSource {

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