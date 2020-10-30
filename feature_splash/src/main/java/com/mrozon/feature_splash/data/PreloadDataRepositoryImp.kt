package com.mrozon.feature_splash.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.mapper.UserToUserDbMapper
import javax.inject.Inject

class PreloadDataRepositoryImp @Inject constructor(
    private val healthDiaryDao: HealthDiaryDao,
    private val mapper: UserToUserDbMapper
): PreloadDataRepository {

    override fun getLocalUser(): LiveData<User> {
        val userDb = healthDiaryDao.getUser()
        return Transformations.map(userDb) {
            mapper.reverseMap(it)
        }
    }

}