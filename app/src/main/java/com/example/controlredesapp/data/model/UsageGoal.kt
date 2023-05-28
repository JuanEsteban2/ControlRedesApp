package com.example.controlredesapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class UsageGoalList(val results: List<UsageGoalEntity> = listOf())

// Room:
@Entity
data class UsageGoalEntity(
    @PrimaryKey
    val name: String = "",
    @ColumnInfo(name = "hoursGoal")
    val hoursGoal: Double = 0.0,
    @ColumnInfo(name = "current_average")
    val current_average: Double = 0.0,
    @ColumnInfo(name = "previous_average")
    val previous_average: Double = 0.0,
    @ColumnInfo(name = "difference")
    val difference: Double = 0.0
)