package com.example.controlredesapp.data.local

import com.example.controlredesapp.data.local.daos.LogRedDao
import com.example.controlredesapp.data.local.daos.UsageGoalDao
//import com.example.controlredesapp.data.model.LogRed
import com.example.controlredesapp.data.model.LogRedEntity
import com.example.controlredesapp.data.model.LogRedEntityB
//import com.example.controlredesapp.data.model.UsageGoal
import com.example.controlredesapp.data.model.UsageGoalEntity
import com.example.controlredesapp.data.model.toLogRedEntity
import com.example.controlredesapp.data.model.toLogRedEntityB

//import com.example.controlredesapp.data.model.toLogRed

class LocalRedDataSource(private val logRedDao: LogRedDao, private val usageGoalDao: UsageGoalDao) {

    // Tabla de registros de uso:
    suspend fun getLogRedEntity(red: String): LogRedEntityB =
        logRedDao.getLogRedEntity(red).toLogRedEntityB()

    suspend fun insertLogRedEntity(log: LogRedEntity) {
        logRedDao.insertLogRedEntity(log)
    }

    suspend fun updateLogRedEntity(log: LogRedEntityB) {
        logRedDao.updateLogRedEntity(log.toLogRedEntity())
    }

    // Tabla de objetivos planteados:
    suspend fun getUsageGoalEntity(): List<UsageGoalEntity> {
        return usageGoalDao.getUsageGoalsEntity()
    }

    suspend fun insertUsageGoalEntity(usageGoal: UsageGoalEntity) {
        usageGoalDao.insertUsageGoalEntity(usageGoal)
    }

    suspend fun updateUsageGoalEntity(usageGoal: UsageGoalEntity) {
        usageGoalDao.updateUsageGoalEntity(usageGoal)
    }

    suspend fun deleteUsageGoalEntity(usageGoal: UsageGoalEntity) {
        usageGoalDao.deleteUsageGoalEntity(usageGoal)
    }

}