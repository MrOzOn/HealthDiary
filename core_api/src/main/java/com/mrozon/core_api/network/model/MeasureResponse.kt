package com.mrozon.core_api.network.model

import com.google.gson.annotations.SerializedName
import com.mrozon.core_api.entity.Measure
import com.mrozon.utils.extension.toSimpleDate

data class MeasureResponse(
	val comments: String? = null,
	val value2: String? = null,
	val value1: String,
	val patient: Int,
	val observing: Int,
	val id: Int,
	@SerializedName("created_date")
	val createdDate: String,
	@SerializedName("value_added")
	val valueAdded: String,
	val type: Int,
	@SerializedName("last_modified")
	val lastModified: String
)

fun MeasureResponse.toMeasure(): Measure {
	return Measure(
		id = id.toLong(),
		value1 = value1,
		value2 = value2?:"",
		valueAdded = valueAdded.toSimpleDate(format = "yyyy-MM-dd'T'HH:mm:ss"), //2020-07-23T17:39:02+03:00
		comment = comments?:"",
		personId = patient.toLong(),
		measureTypeId = type.toLong()
	)
}

