package com.mrozon.core_api.network.model

import com.mrozon.core_api.entity.Gender
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.entity.User
import com.mrozon.utils.extension.toSimpleDate

data class RegisterResponse(
    val email: String,
    val first_name: String,
    val id: Int,
    val last_login: Any,
    val last_name: String,
    val username: String
)

fun RegisterResponse.toUser(): User {
    return User(
        id = id.toLong(),
        email = email,
        firstname = first_name,
        lastname = last_name
    )
}