package com.baysoftware.bayfit.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exerciseDao: ExerciseSessionDAO) {

    val allExercises: List<ExerciseSessionEntity> = exerciseDao.getAllSessions()

    @WorkerThread
    suspend fun insert(data: ExerciseSessionEntity) {
        exerciseDao.insert(data)
    }
}