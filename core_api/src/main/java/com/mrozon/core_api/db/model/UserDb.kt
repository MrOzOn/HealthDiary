package com.mrozon.core_api.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var id: Long = 0L,

    @ColumnInfo(name = "user_email")
    val email: String = "",

    @ColumnInfo(name = "user_firstname")
    var firstname: String = "",

    @ColumnInfo(name = "user_token")
    var token: String = "",

    @ColumnInfo(name = "user_lastname")
    var lastname: String = ""
)