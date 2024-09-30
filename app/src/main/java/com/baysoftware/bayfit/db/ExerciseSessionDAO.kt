package com.baysoftware.bayfit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseSessionDAO {

    @Insert
    suspend fun insertSession(session: ExerciseSessionEntity)  // Use 'suspend' para permitir corrotinas

    @Query("SELECT * FROM exercise_sessions")
    fun getAllSessions(): List<ExerciseSessionEntity>

    @Query("SELECT * FROM exercise_sessions WHERE id = :id")
    fun getSessionById(id: Long): ExerciseSessionEntity

    @Query("DELETE FROM exercise_sessions WHERE id = :id")
    suspend fun deleteSession(id: Long)

}
