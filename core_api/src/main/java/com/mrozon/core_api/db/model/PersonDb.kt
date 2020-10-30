package com.mrozon.core_api.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mrozon.core_api.db.BlobTransmogrifier
import java.util.Date

@Entity(tableName = "person_table")
@TypeConverters(BlobTransmogrifier::class)
data class PersonDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_id")
    var id: Long = 0L,

    @ColumnInfo(name = "person_name")
    val name: String = "",

    @ColumnInfo(name = "person_gender")
    var gender: Int = 0,

    @ColumnInfo(name = "user_born")
    var born: Date = Date()
)