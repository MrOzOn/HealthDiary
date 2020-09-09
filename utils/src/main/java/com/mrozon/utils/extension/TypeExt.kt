package com.mrozon.utils.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val defaultFormat = "yyyy-MM-dd"

fun String.toSimpleDate(format: String = defaultFormat): Date {
    return SimpleDateFormat(format, Locale.getDefault()).parse(this)!!
}

fun Date.toDateString(format: String = defaultFormat): String {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(this)
}