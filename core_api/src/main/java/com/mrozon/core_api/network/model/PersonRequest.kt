package com.mrozon.core_api.network.model

data class PersonRequest(
    val born: String,
    val gender: Boolean,
    val name: String
)