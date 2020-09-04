package com.mrozon.utils.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toSimpleDate(format: String = "YYYY-MM-DD"): Date {
    return SimpleDateFormat(format, Locale.getDefault()).parse(this)!!
}