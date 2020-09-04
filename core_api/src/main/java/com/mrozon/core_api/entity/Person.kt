package com.mrozon.core_api.entity

import java.util.Date

data class Person (
    var id: Long = 0L,
    var name: String,
    var gender: Gender = Gender.MALE,
    var born: Date
)

enum class Gender(val code: Int) {
    MALE(0),
    FEMALE(1)}