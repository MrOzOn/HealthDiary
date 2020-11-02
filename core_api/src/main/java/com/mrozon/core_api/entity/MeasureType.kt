package com.mrozon.core_api.entity


data class MeasureType (
    val id: Long = 0L,
    val name: String = "",
    val mark: String = "",
    val regexp: String = "",
    val hint: String = "",
    val url: String = ""
)