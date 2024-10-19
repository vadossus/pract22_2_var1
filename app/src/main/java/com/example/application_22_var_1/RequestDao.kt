package com.example.application_22_var_1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RequestDao {
    @Insert
    fun insert(requestItem: RequestItem)

    @Query("SELECT * FROM request_table")
    fun getAllRequestItems(): LiveData<List<RequestItem>>
}