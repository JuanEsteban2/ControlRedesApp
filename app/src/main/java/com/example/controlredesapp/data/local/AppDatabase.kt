package com.example.controlredesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.controlredesapp.data.local.daos.LogRedDao
import com.example.controlredesapp.data.local.daos.UsageGoalDao
import com.example.controlredesapp.data.model.LogRedEntity
import com.example.controlredesapp.data.model.UsageGoalEntity

@Database(entities = [LogRedEntity::class, UsageGoalEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun logRedDao(): LogRedDao

    abstract fun usageGoalDao(): UsageGoalDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_redes"
            ).build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }

}