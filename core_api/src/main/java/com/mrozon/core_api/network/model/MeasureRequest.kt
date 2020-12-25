package com.mrozon.core_api.network.model

data class MeasureRequest(
    val value1: String,
    val value2: String,
    val value_added: String,
    val comments: String,
    val type: Long,
    val patient: Long,
    val observing: Long
)