package com.baysoftware.bayfit.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.baysoftware.bayfit.db.ExerciseSessionDAO
import com.baysoftware.bayfit.db.ExerciseSessionEntity

class ExerciseRepository(private val exerciseSessionDAO: ExerciseSessionDAO) {

    val allExercises: List<ExerciseSessionEntity> = exerciseSessionDAO.getAllSessions()


    companion object{
        //For Singleton instatiation
        @Volatile private var instance: ExerciseRepository? = null
        fun getInstance(exerciseSessionDAO: ExerciseSessionDAO) =
            instance ?: synchronized(this) {
                instance ?: ExerciseRepository(exerciseSessionDAO).also { instance = it}
            }
    }

    }