package com.baysoftware.bayfit.db

import androidx.lifecycle.LiveData

class ExerciseRepository(private val exerciseSessionDAO: ExerciseSessionDAO) {

    val allExercises: LiveData<List<ExerciseSessionEntity>> = exerciseSessionDAO.getAllSessions()

    suspend fun insertSession(session: ExerciseSessionEntity) {
        exerciseSessionDAO.insertSession(session)
    }

    companion object {
        @Volatile private var instance: ExerciseRepository? = null
        fun getInstance(exerciseSessionDAO: ExerciseSessionDAO) =
            instance ?: synchronized(this) {
                instance ?: ExerciseRepository(exerciseSessionDAO).also { instance = it }
            }
    }
}
