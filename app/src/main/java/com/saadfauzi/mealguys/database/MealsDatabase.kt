package com.saadfauzi.mealguys.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MealsEntity::class], version = 1, exportSchema = false)
abstract class MealsDatabase: RoomDatabase() {
    abstract fun mealsDao(): MealsDao

    companion object {
        @Volatile
        private var INSTANCE: MealsDatabase? = null
        fun getInstance(context: Context): MealsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MealsDatabase::class.java,
                    "meals"
                )
                    .build()
            }
    }
}