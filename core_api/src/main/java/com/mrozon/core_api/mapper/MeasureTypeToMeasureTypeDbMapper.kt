package com.mrozon.core_api.mapper

import com.mrozon.core_api.db.model.MeasureTypeDb
import com.mrozon.core_api.db.model.PersonDb
import com.mrozon.core_api.entity.Gender
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.utils.base.BaseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasureTypeToMeasureTypeDbMapper @Inject constructor(): BaseMapper<MeasureType, MeasureTypeDb>()  {

    override fun map(entity: MeasureType?): MeasureTypeDb? {
        entity?.let {
            return MeasureTypeDb(id = it.id,
                name = it.name,
                hint = it.hint,
                regexp = it.regexp,
                url = it.url,
                mark = it.mark
            )
        }
        return null
    }

    override fun reverseMap(model: MeasureTypeDb?): MeasureType? {
        model?.let {
            return MeasureType(id = it.id,
                name = it.name,
                hint = it.hint,
                regexp = it.regexp,
                url = it.url,
                mark = it.mark
            )
        }
        return null
    }
}