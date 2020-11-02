package com.mrozon.feature_measure_type.data

import android.content.Context
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.mapper.MeasureTypeToMeasureTypeDbMapper
import com.mrozon.core_api.mapper.PersonToPersonDbMapper
import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.core_api.network.model.toMeasureType
import com.mrozon.core_api.network.model.toPerson
import com.mrozon.utils.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasureTypeRepositoryImpl @Inject constructor(private val dao: HealthDiaryDao,
        private val measureTypeRemoteDataSource: MeasureTypeRemoteDataSource,
        private val mapper: MeasureTypeToMeasureTypeDbMapper,
        private val context: Context
): MeasureTypeRepository {

    override fun getMeasureTypes(): Flow<Result<List<MeasureType>>> {
        return flow {
            emit(Result.loading())
            val query = dao.getMeasureTypes()
            val result = query.firstOrNull()
            result?.let {
                emit(Result.success(mapper.reverseMap(it)))
            }
            val networkResult = measureTypeRemoteDataSource.getMeasureTypes()
            if (networkResult.status == Result.Status.SUCCESS) {
                val data = networkResult.data!!
                val measureTypes = data.map { response ->
                    response.toMeasureType()
                }
                val measureTypesDb = mapper.map(measureTypes)
                dao.reloadMeasureType(measureTypesDb)
                emit(Result.success(measureTypes))
            } else if (networkResult.status == Result.Status.ERROR) {
                emit(Result.error(networkResult.message!!))
            }
        }
    }

    override fun refreshMeasureTypes(): Flow<Result<List<MeasureType>>> {
        return flow {
            emit(Result.loading())
            val networkResult = measureTypeRemoteDataSource.getMeasureTypes()
            if (networkResult.status == Result.Status.SUCCESS) {
                val data = networkResult.data!!
                val measureTypes = data.map { response ->
                    preloadNetworkImage(response.url)
                    response.toMeasureType()
                }
                val measureTypesDb = mapper.map(measureTypes)
                dao.reloadMeasureType(measureTypesDb)
                emit(Result.success(measureTypes))
            } else if (networkResult.status == Result.Status.ERROR) {
                emit(Result.error(networkResult.message!!))
            }
        }
    }

    private fun preloadNetworkImage(url: String){
        val imageLoader = context.imageLoader
        val request = ImageRequest.Builder(context)
            .data(HealthDiaryService.ENDPOINT +url)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .build()
        imageLoader.enqueue(request)
    }
}