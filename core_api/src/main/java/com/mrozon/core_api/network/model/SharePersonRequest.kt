package com.mrozon.core_api.network.model

data class SharePersonRequest(
    val patient_id: Int,
    val username: String
)