package com.example.controlredesapp.domain

import com.example.controlredesapp.data.local.LocalRedDataSource
//import com.example.controlredesapp.data.model.LogRed
import com.example.controlredesapp.data.model.LogRedEntity
import com.example.controlredesapp.data.model.LogRedEntityB
//import com.example.controlredesapp.data.model.UsageGoal
import com.example.controlredesapp.data.model.UsageGoalEntity

class RedRepositoryImpl(private val localRedDataSource: LocalRedDataSource) : RedRepository {

    // Tabla registros de uso
    override suspend fun getLogRedEntity(red: String): LogRedEntityB =
        localRedDataSource.getLogRedEntity(red)

    override suspend fun insertLogRedEntity(log: LogRedEntity) {
        localRedDataSource.insertLogRedEntity(log)
    }

    override suspend fun updateLogRedEntity(log: LogRedEntityB) {
        localRedDataSource.updateLogRedEntity(log)
    }

    // Tabla objetivos de uso
    override suspend fun getUsageGoalsEntity(): List<UsageGoalEntity> =
        localRedDataSource.getUsageGoalEntity()

    override suspend fun insertUsageGoalEntity(usageGoal: UsageGoalEntity) {
        localRedDataSource.insertUsageGoalEntity(usageGoal)
    }

    override suspend fun updateUsageGoalEntity(usageGoal: UsageGoalEntity) {
        localRedDataSource.updateUsageGoalEntity(usageGoal)
    }

    override suspend fun deleteUsageGoalEntity(usageGoal: UsageGoalEntity) {
        localRedDataSource.deleteUsageGoalEntity(usageGoal)
    }
}