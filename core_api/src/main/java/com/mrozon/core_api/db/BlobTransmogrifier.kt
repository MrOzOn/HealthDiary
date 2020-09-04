package com.mrozon.core_api.db

import androidx.room.TypeConverter
import java.util.Date

class BlobTransmogrifier {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}