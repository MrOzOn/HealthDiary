package com.mrozon.core_api.network.model

data class RegisterResponse(
    val email: String,
    val first_name: String,
    val id: Int,
    val last_login: Any,
    val last_name: String,
    val username: String
)