package com.mrozon.core_api.network.model

import com.mrozon.core_api.db.model.UserDb
import com.mrozon.core_api.entity.User

data class LoginResponse(
    val email: String,
    val first_name: String,
    val last_name: String,
    val token: String,
    val user_id: Int
)

fun LoginResponse.toUserDb(): UserDb {
    return UserDb(
        id = user_id.toLong(),
        email = email,
        firstname = first_name,
        lastname = last_name,
        token = ""
    )
}

fun LoginResponse.toUser(): User {
    return User(
        id = user_id.toLong(),
        email = email,
        firstname = first_name,
        lastname = last_name
    )
}