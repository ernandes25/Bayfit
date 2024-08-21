package com.baysoftware.bayfit.db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


class MyApplication : Application() {
    private val database by lazy { ExerciseDatabase.getDatabase(this) }

}

@Dao
interface ExerciseSessionDAO {
    @Insert
    suspend fun insertSession(session: ExerciseSessionEntity)

    @Query("SELECT * FROM exercise_sessions ORDER BY date DESC")
    fun getAllSessions(): LiveData<List<ExerciseSessionEntity>>

    @Query("SELECT * FROM exercise_sessions WHERE id = :id")
    fun getSessionById(id: Long): LiveData<ExerciseSessionEntity>
}
