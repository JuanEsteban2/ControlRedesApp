package com.example.controlredesapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.controlredesapp.data.model.LogRedEntity

@Dao
interface LogRedDao {

    @Query("SELECT * FROM LogRedEntity WHERE name = :red")
    suspend fun getLogRedEntity(red: String): LogRedEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogRedEntity(log: LogRedEntity)

    @Update
    suspend fun updateLogRedEntity(log: LogRedEntity)

}