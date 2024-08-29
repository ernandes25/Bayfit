package com.baysoftware.bayfit.history.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.baysoftware.bayfit.db.ExerciseSessionEntity
import com.baysoftware.bayfit.db.ExerciseDatabase
import com.baysoftware.bayfit.db.ExerciseSessionDAO
import kotlinx.coroutines.launch

class HistoryListViewModel(application: Application) : AndroidViewModel(application) {

    private val exerciseSessionDAO: ExerciseSessionDAO
    val exerciseSessions: LiveData<List<ExerciseSessionEntity>>

    init {
        val database = ExerciseDatabase.getDatabase(application)
        exerciseSessionDAO = database.exerciseSessionDAO()
        exerciseSessions = exerciseSessionDAO.getAllSessions()
    }

    fun insertSession(session: ExerciseSessionEntity) {
        viewModelScope.launch {
            exerciseSessionDAO.insertSession(session)
        }
    }

    fun getSessionById(id: Long): LiveData<ExerciseSessionEntity> {
        return exerciseSessionDAO.getSessionById(id)
    }
}