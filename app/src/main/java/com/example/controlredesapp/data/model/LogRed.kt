package com.example.controlredesapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Room:
@Entity
data class LogRedEntity(
    @PrimaryKey
    val name: String = "",
    @ColumnInfo(name = "day1")
    val day1: Double = 0.0,
    @ColumnInfo(name = "day2")
    val day2: Double = 0.0,
    @ColumnInfo(name = "day3")
    val day3: Double = 0.0,
    @ColumnInfo(name = "day4")
    val day4: Double = 0.0,
    @ColumnInfo(name = "day5")
    val day5: Double = 0.0,
    @ColumnInfo(name = "day6")
    val day6: Double = 0.0,
    @ColumnInfo(name = "day7")
    val day7: Double = 0.0,
    @ColumnInfo(name = "day8")
    val day8: Double = 0.0,
    @ColumnInfo(name = "day9")
    val day9: Double = 0.0,
    @ColumnInfo(name = "day10")
    val day10: Double = 0.0
)

data class LogRedEntityB( // Entidad mejorada para manejar el conjunto de registros más fácilmente
    // dentro de la app, no en la base de datos
    val name: String = "",
    val usageLogs: MutableList<Double> = mutableListOf()
)

fun LogRedEntity.toLogRedEntityB(): LogRedEntityB {
    val usageLogsList: MutableList<Double> = mutableListOf()
    usageLogsList.add(this.day1)
    usageLogsList.add(this.day2)
    usageLogsList.add(this.day3)
    usageLogsList.add(this.day4)
    usageLogsList.add(this.day5)
    usageLogsList.add(this.day6)
    usageLogsList.add(this.day7)
    usageLogsList.add(this.day8)
    usageLogsList.add(this.day9)
    usageLogsList.add(this.day10)

    return LogRedEntityB(this.name, usageLogsList)
}

fun LogRedEntityB.toLogRedEntity(): LogRedEntity = LogRedEntity(
    this.name,
    this.usageLogs[0],
    this.usageLogs[1],
    this.usageLogs[2],
    this.usageLogs[3],
    this.usageLogs[4],
    this.usageLogs[5],
    this.usageLogs[6],
    this.usageLogs[7],
    this.usageLogs[8],
    this.usageLogs[9]
)