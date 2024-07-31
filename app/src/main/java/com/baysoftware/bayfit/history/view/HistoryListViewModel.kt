package com.baysoftware.bayfit.history.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.baysoftware.bayfit.db.ExerciseRepository
import com.baysoftware.bayfit.db.BayfitDatabase
import com.baysoftware.bayfit.db.ExerciseSessionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExerciseRepository
    val allSessions: LiveData<List<ExerciseSessionEntity>>

    init {
        val exerciseSessionDAO = BayfitDatabase.getDatabase(application).exerciseSessionDAO()
        repository = ExerciseRepository.getInstance(exerciseSessionDAO)
        allSessions = repository.allExercises
    }

    fun insertSession(session: ExerciseSessionEntity) {
        viewModelScope.launch(Dispatchers.IO) {  // Use Dispatchers.IO para operações de E/S
            repository.insertSession(session)
        }
    }
}
