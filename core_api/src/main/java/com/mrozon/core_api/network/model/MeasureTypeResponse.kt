package com.mrozon.core_api.network.model

import com.mrozon.core_api.entity.Gender
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.utils.extension.toSimpleDate

data class MeasureTypeResponse(
	val regexp: String,
	val hint: String,
	val name: String,
	val id: Int,
	val mark: String,
	val url: String
)

fun MeasureTypeResponse.toMeasureType(): MeasureType {
	return MeasureType(
		id = id.toLong(),
		name = name,
		mark = mark,
		hint = hint,
		url = url,
		regexp = regexp
	)
}