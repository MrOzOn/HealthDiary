package com.mrozon.feature_splash.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.mapper.MeasureTypeToMeasureTypeDbMapper
import com.mrozon.core_api.mapper.UserToUserDbMapper
import com.mrozon.core_api.network.model.toMeasureType
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
    private val remoteDataSource: PreloadDataRemoteDataSource,
    private val mapper: UserToUserDbMapper,
    private val mapperMT: MeasureTypeToMeasureTypeDbMapper
): PreloadDataRepository {

    override fun getPreloadData(): Flow<Result<User?>> {
        return flow {
            emit(Result.loading())
            val networkResult = remoteDataSource.getMeasureTypes()
            if (networkResult.status == Result.Status.SUCCESS) {
                val data = networkResult.data!!
                val measureTypes = data.map { measureType ->
                    measureType.toMeasureType()
                }
                val measureTypesDb = mapperMT.map(measureTypes)
                dao.reloadMeasureType(measureTypesDb)
            }
            val query = dao.getUser()
            val userDb = query.firstOrNull()
            if(userDb==null){
                emit(Result.success(null))
            }
            else
            {
                emit(Result.success(mapper.reverseMap(userDb)))
            }
        }
    }

}