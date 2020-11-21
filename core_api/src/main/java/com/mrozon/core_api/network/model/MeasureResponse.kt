package com.mrozon.core_api.network.model

import com.mrozon.core_api.entity.Measure
import com.mrozon.utils.extension.toSimpleDate

data class MeasureResponse(
	val comments: String? = null,
	val value2: String? = null,
	val value1: String,
	val patient: Int,
	val observing: Int,
	val id: Int,
	val createdDate: String,
	val valueAdded: String,
	val type: Int,
	val lastModified: String
)

fun MeasureResponse.toMeasure(): Measure {
	return Measure(
		id = id.toLong(),
		value1 = value1,
		value2 = value2?:"",
		valueAdded = valueAdded.toSimpleDate(format = "yyyy-MM-dd'T'HH:mm:ss.SSXXX"), //2020-07-23T17:39:02+03:00
		comment = comments?:"",
		personId = patient.toLong(),
		measureTypeId = type.toLong()
	)
}

