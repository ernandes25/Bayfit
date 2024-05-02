package com.baysoftware.bayfit.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exerciseDao: ExerciseSessionDAO) {

    val allExercises: Flow<List<ExerciseSessionEntity>> = exerciseDao.getAlphabetizedExerciseSession()

    @WorkerThread
    suspend fun insert(data: ExerciseSessionEntity) {
        exerciseDao.insert(data)
    }
}