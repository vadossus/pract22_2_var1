package com.example.application_22_var_1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RequestItem::class], version = 1, exportSchema = false)
abstract class RequestDatabase: RoomDatabase() {
    abstract fun requestDao(): RequestDao
}