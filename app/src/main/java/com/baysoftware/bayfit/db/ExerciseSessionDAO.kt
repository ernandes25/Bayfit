package com.baysoftware.bayfit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseSessionDAO {
    @Query("SELECT * FROM exercise_session")
    fun getAllSessions(): LiveData<List<ExerciseSessionEntity>>

    @Insert
    suspend fun insertSession(session: ExerciseSessionEntity)  // Use 'suspend' para permitir corrotinas
}
