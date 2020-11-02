package com.mrozon.core_api.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measure_type_table")
data class MeasureTypeDb (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "measure_type_id")
    var id: Long = 0L,

    @ColumnInfo(name = "measure_type_name")
    val name: String = "",

    @ColumnInfo(name = "measure_type_mark")
    val mark: String = "",

    @ColumnInfo(name = "measure_type_regexp")
    val regexp: String = "",

    @ColumnInfo(name = "measure_type_hint")
    val hint: String = "",

    @ColumnInfo(name = "measure_type_url")
    val url: String = ""
)