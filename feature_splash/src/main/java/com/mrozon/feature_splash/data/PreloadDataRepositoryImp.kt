package com.mrozon.feature_splash.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.network.model.toPerson
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreloadDataRepositoryImp @Inject constructor(
    private val dao: HealthDiaryDao,
    private val mapper: UserToUserDbMapper
): PreloadDataRepository {

    override fun getLocalUser(): Flow<Result<User?>> {
        return flow {
            emit(Result.loading())
            val query = dao.getUser()
            val userDb = query.firstOrNull()
            if(userDb==null){
                emit(Result.success(null))
            }
            else
            {
                //TODO load additional entities from network - measure types
                emit(Result.success(mapper.reverseMap(userDb)))
            }
        }
    }

}