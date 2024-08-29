package com.baysoftware.bayfit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseSessionDAO {

    @Insert
    suspend fun insertSession(session: ExerciseSessionEntity)  // Use 'suspend' para permitir corrotinas

    @Query("SELECT * FROM exercise_session")
    fun getAllSessions(): List<ExerciseSessionEntity>

    @Query("DELETE FROM exercise_session WHERE id = :id")
    suspend fun deleteSession(id: Long)

}
