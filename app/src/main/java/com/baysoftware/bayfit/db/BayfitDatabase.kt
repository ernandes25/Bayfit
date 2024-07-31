package com.baysoftware.bayfit.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(entities = [ExerciseSessionEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverters::class)
abstract class BayfitDatabase : RoomDatabase() {

    abstract fun exerciseSessionDAO(): ExerciseSessionDAO

    companion object {
        @Volatile
        private var INSTANCE: BayfitDatabase? = null

        fun getDatabase(context: Context): BayfitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BayfitDatabase::class.java,
                    "bayfit_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
