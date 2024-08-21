package com.baysoftware.bayfit.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baysoftware.bayfit.db.ExerciseDatabase
import com.baysoftware.bayfit.db.ExerciseSessionDAO

class TimerViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            val exerciseSessionDAO: ExerciseSessionDAO = ExerciseDatabase.getDatabase(application).exerciseSessionDAO()
            return TimerViewModel(exerciseSessionDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
