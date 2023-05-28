package com.example.controlredesapp.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.controlredesapp.data.model.UsageGoalEntity

@Dao
interface UsageGoalDao {

    @Query("SELECT * FROM UsageGoalEntity")
    suspend fun getUsageGoalsEntity(): List<UsageGoalEntity>

    @Insert
    suspend fun insertUsageGoalEntity(usageGoal: UsageGoalEntity)

    @Update
    suspend fun updateUsageGoalEntity(usageGoal: UsageGoalEntity)

    @Delete
    suspend fun deleteUsageGoalEntity(usageGoal: UsageGoalEntity)

}