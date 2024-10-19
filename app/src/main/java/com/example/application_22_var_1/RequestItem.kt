package com.example.application_22_var_1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "request_table")
data class RequestItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: String,
    val description: String,
    val imageResource: String
)