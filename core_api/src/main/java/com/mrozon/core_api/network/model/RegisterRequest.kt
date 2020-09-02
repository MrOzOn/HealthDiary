package com.mrozon.core_api.network.model

data class RegisterRequest(
    val email: String,
    val first_name: String,
    val last_name: String,
    val password: String,
    val username: String
)