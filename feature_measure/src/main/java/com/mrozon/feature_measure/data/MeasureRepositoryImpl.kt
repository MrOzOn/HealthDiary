package com.mrozon.feature_measure.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.mapper.PersonToPersonDbMapper
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasureRepositoryImpl @Inject constructor(
    private val dao: HealthDiaryDao,
    private val mapper: PersonToPersonDbMapper
): MeasureRepository {

    override fun getPerson(id: Long): Flow<Result<Person>> {
        return flow {
            emit(Result.loading())
            try {
                val response = dao.getPerson(id)
                emit(Result.success(mapper.reverseMap(response)!!))
            }
            catch (e: Exception){
                emit(Result.error(e.message!!))
            }
        }
    }


}