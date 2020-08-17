package com.mrozon.feature_auth_api.domain.entities

data class User(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String)
