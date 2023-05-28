package com.example.controlredesapp.domain

//import com.example.controlredesapp.data.model.LogRed
import com.example.controlredesapp.data.model.LogRedEntity
import com.example.controlredesapp.data.model.LogRedEntityB
//import com.example.controlredesapp.data.model.UsageGoal
import com.example.controlredesapp.data.model.UsageGoalEntity

interface RedRepository {

    // Tabla registros de uso:
    suspend fun getLogRedEntity(red: String): LogRedEntityB

    suspend fun insertLogRedEntity(log: LogRedEntity)

    suspend fun updateLogRedEntity(log: LogRedEntityB)

    //Tabla objetivos:
    suspend fun getUsageGoalsEntity(): List<UsageGoalEntity>

    suspend fun insertUsageGoalEntity(usageGoal: UsageGoalEntity)

    suspend fun updateUsageGoalEntity(usageGoal: UsageGoalEntity)

    suspend fun deleteUsageGoalEntity(usageGoal: UsageGoalEntity)

}