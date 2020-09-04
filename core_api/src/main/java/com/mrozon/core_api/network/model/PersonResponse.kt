package com.mrozon.core_api.network.model

import com.mrozon.core_api.entity.Gender
import com.mrozon.core_api.entity.Person
import com.mrozon.utils.extension.toSimpleDate

data class PersonResponse(
    val avatar: Any,
    val born: String,
    val created_date: String,
    val gender: Boolean,
    val id: Int,
    val name: String,
    val owners: List<Int>
)

fun PersonResponse.toPerson(): Person{
    return Person(
        id = id.toLong(),
        name = name,
        gender = if (gender) Gender.MALE else Gender.FEMALE,
        born = born.toSimpleDate()
    )
}