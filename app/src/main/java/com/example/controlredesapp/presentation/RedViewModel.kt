package com.example.controlredesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.controlredesapp.core.Result
import com.example.controlredesapp.data.model.LogRedEntity
import com.example.controlredesapp.data.model.LogRedEntityB
import com.example.controlredesapp.data.model.UsageGoalEntity
import com.example.controlredesapp.domain.RedRepository
import kotlinx.coroutines.Dispatchers

class RedViewModel(private val repo: RedRepository) : ViewModel() {

    // Tabla registros de uso:
    // Traer todos los registros de una red social (red) específica
    fun fetchLogRedEntity(red: String) = liveData(Dispatchers.IO) {

        emit(Result.Loading())
        try {
            emit(Result.Success(repo.getLogRedEntity(red)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }

    }

    // Crear un registro sobre una red social (red) específica
    fun newLogRedEntity(name: String, latestUsage: Double) = liveData(Dispatchers.IO) {

        emit(Result.Loading())
        try {
            emit(Result.Success(repo.insertLogRedEntity(LogRedEntity(name, day10 = latestUsage))))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }

    }

    // Actualizar un registro de uso para una red específica
    fun changeLogRedEntity(name: String, latestUsage: Double) = liveData(Dispatchers.IO) {

        emit(Result.Loading())
        try {
            emit(Result.Success(repo.updateLogRedEntity(recreateOldLogEntity(name, latestUsage))))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }

    }


    // Tabla objetivos de uso:
    fun fetchUsageGoalEntity() = liveData(Dispatchers.IO) {

        emit(Result.Loading())
        try {
            emit(Result.Success(repo.getUsageGoalsEntity()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }

    }

    fun newUsageGoalEntity(name: String, goal: Double) = liveData(Dispatchers.IO) {

        emit(Result.Loading())
        try {
            emit(Result.Success(repo.insertUsageGoalEntity(UsageGoalEntity(name, goal))))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }

    }

    fun changeUsageGoalEntity(name: String, hours: Double) = liveData(Dispatchers.IO) {

        emit(Result.Loading())
        try {
            emit(
                Result.Success(
                    repo.updateUsageGoalEntity(
                        recreateOldUsageGoalEntity(
                            name,
                            hours
                        )
                    )
                )
            )
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }

    }

    fun eraseUsageGoalEntity(name: String) = liveData(Dispatchers.IO) {

        emit(Result.Loading())
        try {
            emit(Result.Success(repo.deleteUsageGoalEntity(searchUsageGoalEntity(name))))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }

    }


    // Funciones adicionales, para poder invocar correctamente los métodos del repositorio:
    private suspend fun recreateOldLogEntity(
        name: String,
        latestUsage: Double
    ): LogRedEntityB { // Actualización del registro de uso para una red específica, con el último dato ingresado
        val oldLogEntity = repo.getLogRedEntity(name)
        oldLogEntity.usageLogs.removeAt(0)
        oldLogEntity.usageLogs.add(latestUsage)

        return LogRedEntityB(
            name,
            oldLogEntity.usageLogs
        )
    }

    private suspend fun recreateOldUsageGoalEntity(
        name: String,
        hours: Double
    ): UsageGoalEntity { // Actualización de el objetivo de uso, según los datos que ha ingresado recientemente el usuario
        val logRedEntity = repo.getLogRedEntity(name)
        val result = repo.getUsageGoalsEntity()

        var goal = 0.0
        var difference = 0.0
        var currentAverage = 0.0
        var previousAverage = 0.0

        result.forEach {
            if (it.name == name) {
                goal = if (hours != 0.0) {
                    hours
                } else {
                    it.hoursGoal
                }

                currentAverage = logRedEntity.usageLogs.average()
                previousAverage = it.current_average
                difference = currentAverage - it.current_average
            }
        }

        return UsageGoalEntity(name, goal, currentAverage, previousAverage, difference)
    }

    private suspend fun searchUsageGoalEntity(name: String): UsageGoalEntity { // Se busca una entidad de las disponibles en tabla de objetivos para borrar
        val result = repo.getUsageGoalsEntity()
        var entity: UsageGoalEntity? = null

        result.forEach {
            if (it.name == name) {
                entity = it
            }
        }
        return entity!!
    }

}

// Clase para crear instancia del repositorio y pasarla al ViewModel
class RedViewModelFactory(private val repo: RedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RedRepository::class.java)
            .newInstance(repo) //ESTO SE PUEDE HACER MÁS SENCILLO...
    }
}