package com.baysoftware.bayfit.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ExerciseSessionEntity::class],
    version = 1
)
abstract class BayfitDatabase : RoomDatabase() {
    abstract fun exercisesDao(): ExerciseSessionDAO

    companion object {
        @Volatile
        private var INSTANCE: BayfitDatabase? = null

        fun getDatabase(context: Context): BayfitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BayfitDatabase::class.java,
                    "bayfit_database"
                ).fallbackToDestructiveMigration()
                 .build()
                INSTANCE = instance
                instance
            }
        }
    }
}