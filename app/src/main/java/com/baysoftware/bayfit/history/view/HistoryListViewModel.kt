package com.baysoftware.bayfit.history.view



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.baysoftware.bayfit.db.BayfitDatabase
import com.baysoftware.bayfit.db.ExerciseRepository
import com.baysoftware.bayfit.db.ExerciseSession


class HistoryListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExerciseRepository
    val allSessions: LiveData<List<ExerciseSession>>

    init {
        val exerciseSessionDAO = BayfitDatabase.getDatabase(application).exerciseSessionDAO()
        repository = ExerciseRepository(exerciseSessionDAO)
        allSessions = repository.allSessions
    }
}