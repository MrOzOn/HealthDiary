package com.mrozon.core_api.entity

data class User (
    var id: Long = 0L,
    val email: String = "",
    var firstname: String = "",
    var token: String = "",
    var lastname: String = ""
)