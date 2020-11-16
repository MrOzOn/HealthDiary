package com.mrozon.core_api.mapper

import com.mrozon.core_api.db.model.MeasureDb
import com.mrozon.core_api.entity.Measure
import com.mrozon.utils.base.BaseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasureToMeasureDbMapper @Inject constructor(): BaseMapper<Measure, MeasureDb>()  {

    override fun map(entity: Measure?): MeasureDb? {
        entity?.let {
            return MeasureDb(id = it.id,
                value1 = it.value1,
                value2 = it.value2,
                added = it.valueAdded,
                comment = it.comment,
                personID = it.personId,
                measureTypeId = it.measureTypeId
            )
        }
        return null
    }

    override fun reverseMap(model: MeasureDb?): Measure? {
        model?.let {
            return Measure(id = it.id,
                value1 = it.value1,
                value2 = it.value2,
                valueAdded = it.added,
                comment = it.comment,
                personId = it.personID,
                measureTypeId = it.measureTypeId
            )
        }
        return null
    }
}