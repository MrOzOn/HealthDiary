package com.mrozon.core_api.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "measure_table")
data class MeasureDb (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "measure_id")
    var id: Long = 0L,

    @ColumnInfo(name = "measure_value1")
    val value1: String = "",

    @ColumnInfo(name = "measure_value2")
    val value2: String = "",

    @ColumnInfo(name = "measure_value_added")
    val added: Date = Date(),

    @ColumnInfo(name = "measure_comment")
    val comment: String = "",

    @ColumnInfo(name = "measure_person")
    val personID: Long = 0L,

    @ColumnInfo(name = "measure_mtype")
    val measureTypeId: Long = 0L
)