package com.mrozon.core_api.entity

import java.util.Date

data class Person (
    val id: Long = 0L,
    val name: String,
    val gender: Gender = Gender.MALE,
    val born: Date
)

enum class Gender(val code: Int) {
    MALE(0),
    FEMALE(1)}