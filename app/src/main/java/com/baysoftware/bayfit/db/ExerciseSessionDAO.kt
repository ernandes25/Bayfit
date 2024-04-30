package com.baysoftware.bayfit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ExerciseSessionDAO {

    @Query("SELECT * FROM exercise_session ORDER BY data, duration, totalTimePause ASC")
    fun getAlphabetizedExerciseSession(): Flow<List<ExerciseSession>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(
        data: ExerciseSession,
        duration: ExerciseSession,
        totalTimePause: ExerciseSession
    )

    @Query("DELETE FROM exercise_session")
    suspend fun deleteAll()
}