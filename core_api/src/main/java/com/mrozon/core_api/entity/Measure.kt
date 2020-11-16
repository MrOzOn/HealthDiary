package com.mrozon.core_api.entity

import java.util.*

data class Measure (
    val id: Long = 0L,
    val value1: String,
    val value2: String,
    val valueAdded: Date,
    val comment: String,
    val personId: Long,
    val measureTypeId: Long
)